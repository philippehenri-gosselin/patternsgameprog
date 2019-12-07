/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap05.ai06.ai.tree.pacman;

import examples.chap05.ai06.ai.tree.Node;
import examples.chap05.ai06.rules.commands.Command;
import examples.chap05.ai06.rules.commands.DirectionCommand;
import examples.chap05.ai06.state.Pacman;
import examples.chap05.ai06.state.PacmanStatus;
import examples.chap05.ai06.state.State;
import java.util.Iterator;
import java.util.List;

public class GhostsNode extends Node {
    
    private final Parameters parameters;
    
    private final Command[] commands;
    
    public GhostsNode(Node parent,Parameters parameters,Command[] commands) {
        super(parent);
        this.parameters = parameters;
        this.commands = commands;
    }

    @Override
    public void createChildren() {
        List<Command> list = parameters.lister.listCommands(parameters.rules.getState(), 0);
        if (!list.isEmpty()) {
            if (depth != 0) {
                State state = parameters.rules.getState();
                Pacman pacman = state.getChars().getPacman();
                Iterator iterator = list.iterator();
                while (iterator.hasNext()) {
                    DirectionCommand command = (DirectionCommand)iterator.next();
                    if (command.getDirection().isOppositeOf(pacman.getDirection())) {
                        iterator.remove();
                    }
                }
            }
        }
        if (list.isEmpty()) {
            children = new Node[1];
            children[0] = new PacmanNode(this,parameters,null);
        }
        else {
            children = new Node[list.size()];
            for (int i=0;i<list.size();i++) {
                children[i] = new PacmanNode(this,parameters,list.get(i));
            }
        }
    }
    
    @Override
    public void updateState() {
        for (int i=1;i<commands.length;i++) {
            if (commands[i] != null) {
                parameters.rules.addCommand(i, commands[i]);
            }
        }
        parameters.rules.addPassiveCommands();
        parameters.rules.update();
        parameters.updateCount ++;
        
        State state = parameters.state;
        Pacman pacman = state.getChars().getPacman();
        if (state.getGumCount() == 0) {
            setValue(10000);
        }
        else if (pacman.getStatus() == PacmanStatus.DEAD) {
            setValue(-10000 + depth);
        }
        else if (depth >= parameters.maxDepth
         || (depth >= parameters.minDepth && parameters.updateCount >= parameters.maxUpdateCount)) {
            setValue(10000 - state.getGumCount());
        }
    }

    @Override
    public void rollbackState() {
        parameters.rules.rollback();
        children = null;
    }

}
