/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.threads02.ai.tree.alphabeta;

import examples.chap06.threads02.ai.tree.Node;
import java.util.Queue;

public class ExploreMaxTask extends Task {

    public ExploreMaxTask(Node node) {
        super(node);
    }

    public void run(Queue<Task> todo) {
        if (node.getDepth() > 2) {
            Node beta = node.getParent();
            Node alpha = beta.getParent();
            if (beta.hasValue() && alpha.hasValue()
             && beta.getValue() < alpha.getValue()) {
                return;
            }
        }
        
        node.updateState();
        if (node.hasValue()) {
            node.rollbackState();
            moveMinValueUp();
            return;
        }        
        
        todo.add(new MaxTask(node));
        for (Node child : node) {
            todo.add(new ExploreMinTask(child));
        }
    }
}
