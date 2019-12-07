/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap06.threads02.ai.tree.pacman;

import examples.chap06.threads02.ai.tree.Node;
import examples.chap06.threads02.rules.commands.Command;
import examples.chap06.threads02.state.Characters;
import examples.chap06.threads02.state.State;

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
