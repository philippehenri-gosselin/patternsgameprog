/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.ai;

import pacman.ai.map.DistanceMap;
import pacman.ai.mapproviders.DistanceMapProvider;
import pacman.ai.tree.Node;
import pacman.ai.tree.Node;
import pacman.ai.tree.alphabeta.AlphaBeta;
import pacman.ai.tree.minimaxmt.MinimaxConsumer;
import pacman.ai.tree.minimaxmt.ParallelMinimax;
import pacman.ai.tree.pacman.GhostsNode;
import pacman.ai.tree.pacman.PacmanNode;
import pacman.ai.tree.pacman.Parameters;
import pacman.rules.Rules;
import pacman.rules.commands.Command;
import pacman.rules.commands.DirectionCommand;
import pacman.state.Characters;
import pacman.state.Direction;
import pacman.state.MobileElement;
import pacman.state.Pacman;
import pacman.state.State;
import java.util.List;
import java.util.Random;

public class ParallelMinimaxAI extends AI {
    
    public ParallelMinimaxAI(State state, CommandsLister commandsLister, Random random, DistanceMapProvider dmProvider) {
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
        ParallelMinimax minimax = new ParallelMinimax(parameters.parallelDepth);
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
