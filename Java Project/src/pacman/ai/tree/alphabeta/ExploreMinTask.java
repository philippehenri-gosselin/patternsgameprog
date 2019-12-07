/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.ai.tree.alphabeta;

import pacman.ai.tree.Node;
import java.util.Queue;

public class ExploreMinTask extends Task {

    public ExploreMinTask(Node node) {
        super(node);
    }

    public void run(Queue<Task> todo) {
        if (node.getDepth() > 2) {
            Node beta = node.getParent();
            Node alpha = beta.getParent();
            if (beta.hasValue() && alpha.hasValue()
             && beta.getValue() > alpha.getValue()) {
                return;
            }
        }        
        
        node.updateState();
        if (node.hasValue()) {
            node.rollbackState();
            moveMaxValueUp();
            return;
        }
        
        todo.add(new MinTask(node));
        for (Node child : node) {
            todo.add(new ExploreMaxTask(child));
        }
    }
}
