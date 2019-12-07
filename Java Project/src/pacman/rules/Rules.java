/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.rules;

import pacman.rules.actions.Action;
import pacman.rules.commands.CollisionsCommand;
import pacman.rules.commands.Command;
import pacman.rules.commands.GumsCommand;
import pacman.rules.commands.MoveCommand;
import pacman.rules.commands.ResurrectionCommand;
import pacman.rules.commands.UpdateStatusCommand;
import pacman.state.Characters;
import pacman.state.State;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;
import java.util.TreeMap;

public class Rules {

    private State state;

    private TreeMap<Integer,Command> commands = new TreeMap();
    
    private Queue<Queue<Action>> actions = Collections.asLifoQueue(new ArrayDeque());
    
    private boolean recordActions = true;
    
    public Rules(State state) {
        this.state = state;
    }
    
    public State getState() {
        return state;
    }
    
    public void setState(State state) {
        commands.clear();
        actions.clear();
        this.state = state;
    }    
    
    public boolean isActionsRecorded() {
        return recordActions;
    }
    
    public void setActionsRecording(boolean b) {
        this.recordActions = b;
    }
    
    public TreeMap<Integer, Command> getCommands() {
        return commands;
    }

    public void setCommands(TreeMap<Integer, Command> commands) {
        this.commands = commands;
    }    
    
    public void addCommand(int priority,Command command) {
        commands.put(priority,command);
    }
    
    public void addPassiveCommands() 
    {
        addCommand(100, new MoveCommand(0));
        addCommand(200, new GumsCommand());
        addCommand(300, new CollisionsCommand());
        addCommand(400, new UpdateStatusCommand(0));

        Characters chars = state.getChars();
        for (int charIndex=1;charIndex<chars.size();charIndex++) {
            addCommand(500+charIndex, new MoveCommand(charIndex));
            addCommand(600+charIndex, new UpdateStatusCommand(charIndex));
            addCommand(700+charIndex, new ResurrectionCommand(charIndex));
        }
        addCommand(800, new CollisionsCommand());
    }

    public void update() { 
        if (!commands.isEmpty()) {
            Queue<Action> currentActions = Collections.asLifoQueue(new ArrayDeque());
            for (Command command : commands.values()) {
                command.execute(currentActions,state);
            }
            if (recordActions) {
                actions.add(currentActions);
            }
            commands.clear();
        }        
        state.incEpoch();
    }
    
    public void rollback() {
        Queue<Action> currentActions = actions.poll();
        while (!currentActions.isEmpty()) {
            currentActions.poll().undo(state);
        }
        state.decEpoch();
    }
    
    public Queue<Queue<Action>> getActions() {
        return actions;
    }
    
    public void clearActions() {
        actions.clear();
    }


}
