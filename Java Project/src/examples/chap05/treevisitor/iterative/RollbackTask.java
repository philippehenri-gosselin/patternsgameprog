/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.treevisitor.iterative;

import java.util.Queue;
import pacman.ai.tree.Node;

public class RollbackTask extends Task {
    public RollbackTask(Node node) {
        super(node);
    }

    public void execute(Queue<Task> queue) {
        node.rollbackState();
    }
}
