/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.ai06.ai;

import examples.chap05.ai06.ai.map.DistanceMap;
import examples.chap05.ai06.ai.mapproviders.DistanceMapProvider;
import examples.chap05.ai06.ai.tree.Node;
import examples.chap05.ai06.ai.tree.alphabeta.AlphaBeta;
import examples.chap05.ai06.ai.tree.pacman.GhostsNode;
import examples.chap05.ai06.ai.tree.pacman.PacmanNode;
import examples.chap05.ai06.ai.tree.pacman.Parameters;
import examples.chap05.ai06.rules.Rules;
import examples.chap05.ai06.rules.commands.Command;
import examples.chap05.ai06.rules.commands.DirectionCommand;
import examples.chap05.ai06.state.Characters;
import examples.chap05.ai06.state.Direction;
import examples.chap05.ai06.state.MobileElement;
import examples.chap05.ai06.state.Pacman;
import examples.chap05.ai06.state.PacmanStatus;
import examples.chap05.ai06.state.State;
import java.util.List;
import java.util.Random;

public class MinimaxAI extends AI {
    
    public MinimaxAI(State state, CommandsLister commandsLister, Random random, DistanceMapProvider dmProvider) {
        super(state, 0, commandsLister, random);
        this.dmProvider = dmProvider;
    }

    public Command createCommand() 
    {        
        Pacman pacman = state.getChars().getPacman();
        if (pacman.getPosition() != 0)
            return null;
        
        Parameters parameters = new Parameters(new Rules(state.clone()),commandsLister);
        GhostsNode root = new GhostsNode(null,parameters,new Command[0]);
        AlphaBeta minimax = new AlphaBeta();
        parameters.updateCount = 0;
        List<Node> bestNodes = minimax.run(root);
        //System.out.println(" *** updates:   "+parameters.updateCount);
        if (bestNodes.isEmpty())
            return null;
        for (int i=0;i<root.getChildCount();i++) {
            PacmanNode n = (PacmanNode)root.getChild(i);
            DirectionCommand c = (DirectionCommand)n.getCommand();
            String dir = (c!=null)?c.getDirection().toString():"null";
          //  System.out.println(dir+":"+n.getValue());
        }
        if (state.getChars().getPacman().getStatus() == PacmanStatus.DEAD) {
            throw new RuntimeException();
        }
        
        if (bestNodes.size() == 1) {
            PacmanNode pacmanNode = (PacmanNode)bestNodes.get(0);
            return pacmanNode.getCommand();
        }
        
        DistanceMap map = dmProvider.getMap("Gums");
        Command bestCommand = null;
        Characters chars = state.getChars();
        MobileElement me = chars.get(charIndex);
        int minWeight = Integer.MAX_VALUE;
        for (Node node : bestNodes) {
            PacmanNode pacmanNode = (PacmanNode)node;
            DirectionCommand dirCommand = (DirectionCommand)pacmanNode.getCommand();
            Direction direction = dirCommand.getDirection();
            int weight = map.getWeight(me.getX(), me.getY(), direction);
            if (weight <= minWeight) {
                minWeight = weight;
                bestCommand = dirCommand;
            }
        }    
        return bestCommand;
    }
}
