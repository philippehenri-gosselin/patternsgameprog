/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package pacman.ai.tree.minimaxmt;

import pacman.ai.tree.Node;
import pacman.ai.tree.alphabeta.AlphaBeta;
import pacman.mt.Task;

public class MinimaxConsumer extends Task {

    private final Node precomputedNode;

    private final Node root;

    public MinimaxConsumer(Node precomputedNode,Node root) {
        this.precomputedNode = precomputedNode;
        this.root = root;
    }
    
    @Override
    public void run() {
        AlphaBeta minimax = new AlphaBeta();
        minimax.run(root);
        precomputedNode.setValue(root.getValue());
    }

}
