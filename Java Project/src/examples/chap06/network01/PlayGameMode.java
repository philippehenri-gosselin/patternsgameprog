/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.network01;

import examples.chap06.network01.sync.RulesThreadObserver;
import examples.chap06.network01.sync.RulesThread;
import examples.chap06.network01.ai.AI;
import examples.chap06.network01.ai.CommandsLister;
import examples.chap06.network01.ai.DefaultCommandsLister;
import examples.chap06.network01.ai.GhostAI;
import examples.chap06.network01.ai.PacmanAI;
import examples.chap06.network01.ai.mapproviders.DistanceMapProvider;
import examples.chap06.network01.ai.mapproviders.GhostsMapMaintainer;
import examples.chap06.network01.ai.mapproviders.CharMapMaintainer;
import examples.chap06.network01.ai.mapproviders.GraveyardMapMaintainer;
import examples.chap06.network01.ai.mapproviders.GumsMapMaintainer;
import examples.chap06.network01.gui.Keyboard;
import examples.chap06.network01.state.Characters;
import examples.chap06.network01.state.Direction;
import examples.chap06.network01.state.State;
import examples.chap06.network01.render.Renderer;
import examples.chap06.network01.rules.commands.DirectionCommand;
import examples.chap06.network01.rules.Rules;
import examples.chap06.network01.rules.commands.Command;
import examples.chap06.network01.state.GhostStatus;
import examples.chap06.network01.state.Pacman;
import examples.chap06.network01.state.PacmanStatus;
import examples.chap06.network01.state.immutable.ImmutableState;
import examples.chap06.network01.state.mutable.MutableState;
import examples.chap06.network01.sync.CachedStateObverser;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;

public class PlayGameMode extends GameMode implements RulesThreadObserver {

    private RulesThread rulesThread;
    
    private Command initCommand;
    
    private Renderer renderer;
    
    private CachedStateObverser cachedStateObserver;
   
    private int showDistanceMap = 0;
    
    private TreeMap<Integer,Command> commands = new TreeMap();
    
    private int currentChar;
    
    private boolean rollback = false;
    
    private DistanceMapProvider dmProvider;
    
    private AI[] ais = new AI[5];     

    private JsonBuilderFactory jsonBuilderFactory;
    
    private ArrayList<JsonObject> replay = new ArrayList();
    
    public PlayGameMode(Command initCommand) {
        this.initCommand = initCommand;
    }   
    
    public void setCurrentChar(int currentChar) {
        this.currentChar = currentChar;
    }
    
    public void init() 
    {
        jsonBuilderFactory = Json.createBuilderFactory(null);

        MutableState state = new MutableState();

        initAI(state);
        
        Rules rules = new Rules(state);
        rules.addCommand(0, initCommand);
        JsonObjectBuilder jsonEpochCommands = jsonBuilderFactory.createObjectBuilder();
        JsonObjectBuilder jsonCommand = jsonBuilderFactory.createObjectBuilder();
        initCommand.toJson(jsonCommand);
        jsonEpochCommands.add("0", jsonCommand);
        replay = new ArrayList();
        replay.add(jsonEpochCommands.build());
        rules.update();        
        renderer = new Renderer(gui);
        renderer.stateChanged(state);

        cachedStateObserver = new CachedStateObverser();
        state.registerObserver(cachedStateObserver);
        
        rulesThread = new RulesThread(rules);
        rulesThread.registerObserver(this);
        rulesThread.start();
    }

    public void initAI(MutableState state) 
    {
        dmProvider = new DistanceMapProvider();
        dmProvider.registerMaintainer("Pacman", new CharMapMaintainer(0));
        dmProvider.registerMaintainer("TrackingGhosts", new GhostsMapMaintainer(GhostStatus.TRACK));
        dmProvider.registerMaintainer("FleeingGhosts", new GhostsMapMaintainer(GhostStatus.FLEE));
        dmProvider.registerMaintainer("Graveyard", new GraveyardMapMaintainer());
        dmProvider.registerMaintainer("Gums", new GumsMapMaintainer());
        state.registerObserver(dmProvider);
        
        Random random = new Random();
        CommandsLister commandsLister = new DefaultCommandsLister();
        ImmutableState imState = new ImmutableState(state);
        ais[0] = new PacmanAI(imState,commandsLister,random,dmProvider);
        for(int charIndex=1;charIndex<5;charIndex++) {
            ais[charIndex] = new GhostAI(imState,charIndex,commandsLister,random,dmProvider);
        }
    }
    
    public void saveState(String fileName) {
        rulesThread.processRules((rules) -> {
            State state = rules.getState();
            state.save(fileName);
            savedState = state.clone();
        });
    }
    
    public void loadState(String fileName) {
        if (!new File(fileName).exists())
            return;
        rulesThread.processRules((rules) -> {
            MutableState state = State.load(fileName);
            if (savedState != null) {
                if (!savedState.equals(state)) {
                    Logger.getLogger("global").warning("Incohérence dans l'état sauvegardé");
                }
            }

            initAI(state);

            rules.setState(state);
            
            renderer.stateChanged(state);
            dmProvider.stateChanged(state);
            
            cachedStateObserver = new CachedStateObverser();
            state.registerObserver(cachedStateObserver);
        });
    }
    
    private State savedState;
    private int charCount = 1;
    public void handleInputs() {
        Keyboard keyboard = gui.getKeyboard();
        switch(keyboard.getLastPressedKey()) {
            case KeyEvent.VK_ESCAPE:
                keyboard.consumeLastPressedKey();
                rulesThread.stopRunning();
                try {
                    rulesThread.join();
                } catch (InterruptedException ex) {
                }
                setPreviousGameMode();
                return;
            case KeyEvent.VK_F1:
                keyboard.consumeLastPressedKey();
                currentChar = 0;
                break;
            case KeyEvent.VK_F2:
                keyboard.consumeLastPressedKey();
                if (charCount > 1)
                    currentChar = 1;
                break;
            case KeyEvent.VK_F3:
                keyboard.consumeLastPressedKey();
                if (charCount > 2)
                    currentChar = 2;
                break;
            case KeyEvent.VK_F4:
                keyboard.consumeLastPressedKey();
                if (charCount > 3)
                    currentChar = 3;
                break;
            case KeyEvent.VK_F5:
                keyboard.consumeLastPressedKey();
                if (charCount > 4)
                    currentChar = 4;
                break;
            case KeyEvent.VK_F6:
                keyboard.consumeLastPressedKey();
                saveState("save.ser");
                break;
            case KeyEvent.VK_F7:
                keyboard.consumeLastPressedKey();
                loadState("save.ser");
                break;
            case KeyEvent.VK_F8:
                keyboard.consumeLastPressedKey();
                showDistanceMap ++;
                if (showDistanceMap > 5) {
                    showDistanceMap = 0;
                }
                String mapName = null;
                switch(showDistanceMap) {
                    case 1: mapName = "Pacman"; break;
                    case 2: mapName = "TrackingGhosts"; break;
                    case 3: mapName = "FleeingGhosts"; break;
                    case 4: mapName = "Graveyard"; break;
                    case 5: mapName = "Gums"; break;
                }
                if (mapName == null)
                    renderer.setDistanceMap(null);
                else
                    renderer.setDistanceMap(dmProvider.getMap(mapName));
                break;
            case KeyEvent.VK_BACK_SPACE:
                keyboard.consumeLastPressedKey();
                rollback = true;
                break;
        }
        

        synchronized(commands) {
            if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)) {
                commands.put(currentChar,new DirectionCommand(currentChar,Direction.EAST));
            }
            if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)) {
                commands.put(currentChar,new DirectionCommand(currentChar,Direction.WEST));
            }
            if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)) {
                commands.put(currentChar,new DirectionCommand(currentChar,Direction.SOUTH));
            }
            if (keyboard.isKeyPressed(KeyEvent.VK_UP)) {
                commands.put(currentChar,new DirectionCommand(currentChar,Direction.NORTH));
            }
        }
        
    }
    
    public void update() {
    }    
    
    @Override
    public void stateUpdating(Rules rules) {
        State state = rules.getState();
        synchronized(renderer) {
            Characters chars = state.getChars();
            charCount = chars.size();
            epochDuration = state.getEpochDuration();
            beginEpoch = System.nanoTime();
            cachedStateObserver.dispatchEvents(renderer);
            cachedStateObserver.clearEvents();
        }
        
        if (rollback && state.getEpoch() <= 1) {
            rollback = false;
            rulesThread.setRollback(rollback);
        }

        if (replay.size() != state.getEpoch())
            throw new RuntimeException("Internal error");
        
        if (rollback) {
            rulesThread.setRollback(rollback);
            replay.remove(state.getEpoch()-1);
        }
        else {
            JsonObjectBuilder jsonEpochCommands = jsonBuilderFactory.createObjectBuilder();
            synchronized(commands) {
                for(Map.Entry<Integer, Command> command : commands.entrySet()) {
                    rules.addCommand(command.getKey(), command.getValue());
                    JsonObjectBuilder jsonCommand = jsonBuilderFactory.createObjectBuilder();
                    command.getValue().toJson(jsonCommand);
                    jsonEpochCommands.add(String.valueOf(command.getKey()), jsonCommand);
                }
                commands.clear();
            }
            for(int charIndex=0;charIndex<5;charIndex++) {
                if (charIndex == currentChar)
                    continue;
                Command command = ais[charIndex].createCommand();
                if (command != null) {
                    rules.addCommand(charIndex, command);
                    JsonObjectBuilder jsonCommand = jsonBuilderFactory.createObjectBuilder();
                    command.toJson(jsonCommand);
                    jsonEpochCommands.add(String.valueOf(charIndex), jsonCommand);
                }
            }
            replay.add(jsonEpochCommands.build());
        }
    }

    @Override
    public void stateUpdated(Rules rules) 
    {
        rollback = rulesThread.getRollback();
        
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
        try {
            Map<String,Boolean> options = new HashMap();
            options.put(JsonGenerator.PRETTY_PRINTING, true);
            JsonWriterFactory writerFactory = Json.createWriterFactory(options);
            JsonWriter jsonWriter = writerFactory.createWriter(new FileWriter("replay.json"));
            JsonArrayBuilder replayBuilder = jsonBuilderFactory.createArrayBuilder();
            for (JsonObject json : replay) {
                replayBuilder.add(json);
            }
            jsonWriter.write(replayBuilder.build());
            jsonWriter.close();
        } catch (IOException ex) {
            throw new RuntimeException("Error when writing replay.json");
        }
        
        State state = rules.getState();
        if (state.getGumCount() == 0) {
            if (currentChar == 0) {
                setGameMode(new GameOverMode("Victory !"));
            }
            else {
                setGameMode(new GameOverMode("You failed!"));
            }
        }
        Characters chars = state.getChars();
        Pacman pacman = chars.getPacman();
        if (pacman != null && pacman.getStatus() == PacmanStatus.DEAD && pacman.getStatusTime() == 0) {
            if (currentChar == 0) {
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
