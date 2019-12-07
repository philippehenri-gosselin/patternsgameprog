/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman;

import pacman.sync.RulesThreadObserver;
import pacman.sync.RulesThread;
import pacman.gui.Keyboard;
import pacman.state.Characters;
import pacman.state.State;
import pacman.render.Renderer;
import pacman.rules.Rules;
import pacman.rules.commands.Command;
import pacman.state.mutable.MutableState;
import pacman.sync.CachedStateObverser;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

public class ReplayGameMode extends GameMode implements RulesThreadObserver {

    private RulesThread rulesThread;
    
    private Renderer renderer;
    
    private CachedStateObverser cachedStateObserver;
   
    private boolean rollback = false;

    private JsonArray replay;
    
    public void init() 
    {
        try {
            JsonReader jsonReader = Json.createReader(new FileReader("replay.json"));
            replay = jsonReader.readArray();
            jsonReader.close();
        } catch (FileNotFoundException ex) {
            throw new RuntimeException("Error when reading replay.json");
        }
        
        MutableState state = new MutableState();

        Rules rules = new Rules(state);
        JsonObject initCommands = replay.getJsonObject(0);
        for (Map.Entry<String, JsonValue> pairs : initCommands.entrySet()) {
            int priority = Integer.parseInt(pairs.getKey());
            Command command = Command.fromJson(pairs.getValue().asJsonObject());
            rules.addCommand(priority, command);
        }
        rules.update();        
        
        renderer = new Renderer(gui);
        renderer.stateChanged(state);

        cachedStateObserver = new CachedStateObverser();
        state.registerObserver(cachedStateObserver);
        
        rulesThread = new RulesThread(rules);
        rulesThread.registerObserver(this);
        rulesThread.start();
    }

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
            case KeyEvent.VK_BACK_SPACE:
                keyboard.consumeLastPressedKey();
                rollback = true;
                break;
        }
    }
    
    public void update() {
    }    
    
    @Override
    public void stateUpdating(Rules rules) {
        State state = rules.getState();
        synchronized(renderer) {
            Characters chars = state.getChars();
            epochDuration = state.getEpochDuration();
            beginEpoch = System.nanoTime();
            cachedStateObserver.dispatchEvents(renderer);
            cachedStateObserver.clearEvents();
        }

        if (!rollback && state.getEpoch() < replay.size()) {
            JsonObject initCommands = replay.getJsonObject(state.getEpoch());
            for (Map.Entry<String, JsonValue> pairs : initCommands.entrySet()) {
                int priority = Integer.parseInt(pairs.getKey());
                Command command = Command.fromJson(pairs.getValue().asJsonObject());
                rules.addCommand(priority, command);
            }
        }
        else {
            rollback = true;
            rulesThread.setRollback(rollback);
        }
    }

    @Override
    public void stateUpdated(Rules rules) 
    {
        rollback = rulesThread.getRollback();        
    }
    
    @Override
    public void gameOver(Rules rules) 
    {
    }   
    
    private long epochDuration = 1;            
    private long beginEpoch = 0;
    public void render(long time) {
        synchronized (renderer) {
            renderer.render(beginEpoch,time,beginEpoch+epochDuration);
        }
    }

}
