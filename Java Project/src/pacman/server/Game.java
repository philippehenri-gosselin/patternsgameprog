/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.server;

import pacman.ai.AI;
import pacman.ai.CommandsLister;
import pacman.ai.DefaultCommandsLister;
import pacman.ai.GhostAI;
import pacman.ai.PacmanAI;
import pacman.ai.mapproviders.CharMapMaintainer;
import pacman.ai.mapproviders.DistanceMapProvider;
import pacman.ai.mapproviders.GhostsMapMaintainer;
import pacman.ai.mapproviders.GraveyardMapMaintainer;
import pacman.ai.mapproviders.GumsMapMaintainer;
import pacman.rules.Rules;
import pacman.rules.commands.Command;
import pacman.rules.commands.LoadLevelCommand;
import pacman.state.Characters;
import pacman.state.GhostStatus;
import pacman.state.Pacman;
import pacman.state.PacmanStatus;
import pacman.state.State;
import pacman.state.immutable.ImmutableState;
import pacman.state.mutable.MutableState;
import pacman.sync.RulesThread;
import pacman.sync.RulesThreadObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class Game implements RulesThreadObserver {

    private RulesThread rulesThread;

    private Players players = new Players();
    
    private AI[] ais = new AI[5];
    
    private TreeMap<Integer,Command> commands = new TreeMap();
    
    private JsonBuilderFactory jsonBuilderFactory;
    
    private ArrayList<JsonObject> replay = new ArrayList();
    
    public Game() {
        jsonBuilderFactory = Json.createBuilderFactory(null);
    }
    
    public Players getPlayers() {
        return players;
    }
    
    public void addCommand(int priority,Command command) {
        synchronized(commands) {
            commands.put(priority,command);
        }
    }
    
    public JsonArray getCommands(int first) {
        synchronized(replay) {
            JsonArrayBuilder replayBuilder = jsonBuilderFactory.createArrayBuilder();
            for (int index=first;index<replay.size();index++) {
                replayBuilder.add(replay.get(index));
            }
            return replayBuilder.build();
        }
    }
    
    public JsonObject getEpochCommands(int epoch) {
        synchronized(replay) {
            return replay.get(epoch);
        }
    }
    
    public synchronized boolean isRunning() {
        return rulesThread != null;
    }
    
    public synchronized void start() 
    {
        System.out.println("Game starting...");
        if (rulesThread != null)
            throw new RuntimeException("Internal error");
    
        MutableState state = new MutableState();
        
        DistanceMapProvider dmProvider = new DistanceMapProvider();
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
        
        Command initCommand = new LoadLevelCommand("level.tmx",2,2);
        Rules rules = new Rules(state);
        rules.addCommand(0, initCommand);
        JsonObjectBuilder jsonEpochCommands = jsonBuilderFactory.createObjectBuilder();
        JsonObjectBuilder jsonCommand = jsonBuilderFactory.createObjectBuilder();
        initCommand.toJson(jsonCommand);
        jsonEpochCommands.add("0", jsonCommand);
        synchronized(replay) {
            replay.clear();
            replay.add(jsonEpochCommands.build());
        }
        synchronized(commands) {
            commands.clear();
        }
        rules.update();    
        
        rulesThread = new RulesThread(rules);
        rulesThread.registerObserver(this);
        rulesThread.start();
        
        System.out.println("Game started");
    }
    
    public void stateUpdating(Rules rules) {
        JsonObjectBuilder jsonEpochCommands = jsonBuilderFactory.createObjectBuilder();
        synchronized(commands) {
            commands.entrySet().forEach((command) -> {
                rules.addCommand(command.getKey(), command.getValue());
            });            
            commands.entrySet().forEach((command) -> {
                JsonObjectBuilder jsonCommand = jsonBuilderFactory.createObjectBuilder();
                command.getValue().toJson(jsonCommand);
                String priority = String.valueOf(command.getKey());
                jsonEpochCommands.add(priority,jsonCommand);
            });
            commands.clear();
        }
        List<Player> playersList = players.getAll();
        for(int index=0;index<playersList.size();index++) {
            if (playersList.get(index) == null || playersList.get(index).isAi()) {
                Command command = ais[index].createCommand();
                if (command != null) {
                    rules.addCommand(index, command);
                    JsonObjectBuilder jsonCommand = jsonBuilderFactory.createObjectBuilder();
                    command.toJson(jsonCommand);
                    String priority = String.valueOf(index);
                    jsonEpochCommands.add(priority, jsonCommand);
                }
            }
        }
        synchronized(replay) {
            replay.add(jsonEpochCommands.build());
        }
    }

    public void stateUpdated(Rules rules) {
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

    public void gameOver(Rules rules) {
        rulesThread = null;
        players.removeAll();
    }
}
