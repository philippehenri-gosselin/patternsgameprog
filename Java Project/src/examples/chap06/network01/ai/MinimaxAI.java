/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.network01.ai;

import examples.chap06.network01.ai.map.DistanceMap;
import examples.chap06.network01.ai.mapproviders.DistanceMapProvider;
import examples.chap06.network01.ai.tree.Node;
import examples.chap06.network01.ai.tree.alphabeta.AlphaBeta;
import examples.chap06.network01.ai.tree.minimaxmt.MinimaxConsumer;
import examples.chap06.network01.ai.tree.minimaxmt.ParallelMinimax;
import examples.chap06.network01.ai.tree.pacman.GhostsNode;
import examples.chap06.network01.ai.tree.pacman.PacmanNode;
import examples.chap06.network01.ai.tree.pacman.Parameters;
import examples.chap06.network01.rules.Rules;
import examples.chap06.network01.rules.commands.Command;
import examples.chap06.network01.rules.commands.DirectionCommand;
import examples.chap06.network01.state.Characters;
import examples.chap06.network01.state.Direction;
import examples.chap06.network01.state.MobileElement;
import examples.chap06.network01.state.Pacman;
import examples.chap06.network01.state.State;
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
        List<Node> bestNodes = minimax.run(root);
        if (bestNodes.isEmpty())
            return null;
        
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
