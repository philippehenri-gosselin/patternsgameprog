/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.network03;

import examples.chap06.network03.sync.RulesThreadObserver;
import examples.chap06.network03.sync.RulesThread;
import examples.chap06.network03.gui.Keyboard;
import examples.chap06.network03.state.Characters;
import examples.chap06.network03.state.State;
import examples.chap06.network03.render.Renderer;
import examples.chap06.network03.rules.Rules;
import examples.chap06.network03.rules.commands.Command;
import examples.chap06.network03.rules.commands.DirectionCommand;
import examples.chap06.network03.state.Direction;
import examples.chap06.network03.state.Pacman;
import examples.chap06.network03.state.PacmanStatus;
import examples.chap06.network03.state.mutable.MutableState;
import examples.chap06.network03.sync.CachedStateObverser;
import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

public class RemoteGameMode extends GameMode implements RulesThreadObserver {

    private RulesThread rulesThread;
    
    private Renderer renderer;
    
    private CachedStateObverser cachedStateObserver;
   
    private ServiceQueries serviceQueries;
    
    private JsonArray scheduledCommands;
    
    private int currentScheduledCommand;
    
    private Future commandFuture;
    
    private int playerId;
    
    private JsonBuilderFactory jsonBuilderFactory;
    
    private Direction commandDirection;

    public RemoteGameMode(JsonArray scheduledCommands,ServiceQueries serviceQueries,int playerId) {
        this.scheduledCommands = scheduledCommands;
        this.serviceQueries = serviceQueries;
        this.playerId = playerId;
        this.jsonBuilderFactory = Json.createBuilderFactory(null);
    }
    
    public void init() 
    {
        MutableState state = new MutableState();

        Rules rules = new Rules(state);
        for(int epoch=0;epoch<scheduledCommands.size();epoch++) {
            JsonObject commands = scheduledCommands.getJsonObject(epoch);
            for (Map.Entry<String, JsonValue> pairs : commands.entrySet()) {
                int priority = Integer.parseInt(pairs.getKey());
                Command command = Command.fromJson(pairs.getValue().asJsonObject());
                rules.addCommand(priority, command);
            }
            if (epoch > 0) {
                rules.addPassiveCommands();
            }
            rules.update();        
        }
        scheduledCommands = null;
        
        renderer = new Renderer(gui);
        renderer.stateChanged(state);

        cachedStateObserver = new CachedStateObverser();
        state.registerObserver(cachedStateObserver);
        
        rulesThread = new RulesThread(rules);
        rulesThread.registerObserver(this);
        rulesThread.setFastMode(true);
        rulesThread.start();
    }
    
    public void handleInputs() {
        Keyboard keyboard = gui.getKeyboard();
        switch(keyboard.getLastPressedKey()) {
            case KeyEvent.VK_ESCAPE:
                keyboard.consumeLastPressedKey();
                serviceQueries.delete("/player", playerId);
                rulesThread.stopRunning();
                try {
                    rulesThread.join();
                } catch (InterruptedException ex) {
                }
                setPreviousGameMode();
                return;
        }
        
        if (commandFuture == null || commandFuture.isDone()) {
            commandFuture = null;
            commandDirection = Direction.NONE;
            if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)) {
                commandDirection = Direction.EAST;
            }
            else if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)) {
                commandDirection = Direction.WEST;
            }
            else if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)) {
                commandDirection = Direction.SOUTH;
            }
            if (keyboard.isKeyPressed(KeyEvent.VK_UP)) {
                commandDirection = Direction.NORTH;
            }
            if (commandDirection != Direction.NONE) {
                commandFuture = ForkJoinPool.commonPool().submit(new Runnable() {
                   public void run() {
                       sendCommand(commandDirection);
                   } 
                });
            }
        }
    }
    
    public void update() {
    }    

    public void sendCommand(Direction direction) {
        JsonObjectBuilder builder = jsonBuilderFactory.createObjectBuilder();
        builder.add("priority", playerId-1);

        JsonObjectBuilder commandBuilder = jsonBuilderFactory.createObjectBuilder();
        Command command = new DirectionCommand(playerId-1,direction);
        command.toJson(commandBuilder);
        builder.add("command", commandBuilder.build());
        
        serviceQueries.put("/command", builder.build());
    }
    
    @Override
    public void stateUpdating(Rules rules) {
        State state = rules.getState();
        synchronized(renderer) {
            epochDuration = state.getEpochDuration();
            beginEpoch = System.nanoTime();
            cachedStateObserver.dispatchEvents(renderer);
            cachedStateObserver.clearEvents();
        }
        
        while(scheduledCommands == null || currentScheduledCommand >= scheduledCommands.size()) {
            JsonObject response = serviceQueries.get("/command",state.getEpoch()+1);
            if (response == null) {
                return;
            }
            scheduledCommands = response.getJsonArray("commands");
            currentScheduledCommand = 0;
        }

        JsonObject commands = scheduledCommands.getJsonObject(currentScheduledCommand);
        commands.entrySet().forEach((pairs) -> {
            int priority = Integer.parseInt(pairs.getKey());
            Command command = Command.fromJson(pairs.getValue().asJsonObject());
            rules.addCommand(priority, command);
        });
        currentScheduledCommand ++;
    }

    @Override
    public void stateUpdated(Rules rules) 
    {
        State state = rules.getState();
        if (state.getGumCount() == 0) {
           rulesThread.stopRunning();
        }
        Characters chars = state.getChars();
        Pacman pacman = chars.getPacman();
        if (pacman != null && pacman.getStatus() == PacmanStatus.DEAD && pacman.getStatusTime() == 0) {
            rulesThread.stopRunning();
        }   
    }
    
    @Override
    public void gameOver(Rules rules) 
    {
        State state = rules.getState();
        if (state.getGumCount() == 0) {
            if (playerId == 1) {
                setGameMode(new GameOverMode("Victory !"));
            }
            else {
                setGameMode(new GameOverMode("You failed!"));
            }
        }
        Characters chars = state.getChars();
        Pacman pacman = chars.getPacman();
        if (pacman != null && pacman.getStatus() == PacmanStatus.DEAD && pacman.getStatusTime() == 0) {
            if (playerId == 1) {
                setGameMode(new GameOverMode("You lost !!"));
            }
            else {
                setGameMode(new GameOverMode("Congratulations !"));
            }
        }
    }   
    
    private long epochDuration = 1;            
    private long beginEpoch = 0;
    public void render(long time) {
        synchronized (renderer) {
            renderer.render(beginEpoch,time,beginEpoch+epochDuration);
        }
    }

}
