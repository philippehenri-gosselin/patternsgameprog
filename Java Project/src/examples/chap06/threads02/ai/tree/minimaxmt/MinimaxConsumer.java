/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap06.threads02.ai.tree.minimaxmt;

import examples.chap06.threads02.ai.tree.Node;
import examples.chap06.threads02.ai.tree.alphabeta.AlphaBeta;
import examples.chap06.threads02.mt.Task;

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
