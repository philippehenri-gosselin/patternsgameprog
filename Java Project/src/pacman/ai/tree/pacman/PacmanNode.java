/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package pacman.ai.tree.pacman;

import pacman.ai.tree.Node;
import pacman.rules.commands.Command;
import pacman.state.Characters;
import pacman.state.State;

public class PacmanNode extends Node {

    private final Parameters parameters;
    
    private final Command command;
    
    public PacmanNode(Node parent,Parameters parameters,Command command) {
        super(parent);
        this.parameters = parameters;
        this.command = command;
    }
    
    public Command getCommand() {
        return command;
    }
   
    @Override
    public void createChildren() {
        State state = parameters.state;
        Characters chars = state.getChars();
        Command[] commands = new Command[chars.size()];
        for (int charIndex=1;charIndex<chars.size();charIndex++) {
            Command command = parameters.ais[charIndex].createCommand();
            commands[charIndex] = command;
        }
        Node node = new GhostsNode(this,parameters,commands);
        children = new Node[] { node };        
    }
    
    @Override
    public void updateState() {
        if (command != null) {
            parameters.rules.addCommand(0, command);
        }
    }

    @Override
    public void rollbackState() {
    }



}
