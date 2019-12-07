/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap05.connect4.ai;

import examples.chap05.connect4.Board;
import examples.chap05.connect4.Command;
import java.util.List;
import java.util.Random;
import pacman.ai.tree.Node;
import pacman.ai.tree.alphabeta.AlphaBeta;

public class MinimaxAI extends AI {

    public MinimaxAI(Board board, Random random) {
        super(board, random, "Minimax");
    }

    @Override
    public Command createCommand() {
        if (board.getWinner() != 0) {
            return null;
        }
        AlphaBeta alphabeta = new AlphaBeta();
        BoardNode root = new BoardNode(board,board.getCurrentPlayer());
        List<Node> bestNodes = alphabeta.run(root);
        if (bestNodes.isEmpty()) {
            return null;
        }
        BoardNode bestNode = (BoardNode)bestNodes.get(random.nextInt(bestNodes.size()));
        return bestNode.getCommand();
    }

}
