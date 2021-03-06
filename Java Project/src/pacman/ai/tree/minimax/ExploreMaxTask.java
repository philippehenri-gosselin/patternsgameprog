/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.ai.tree.minimax;

import pacman.ai.tree.Node;
import java.util.Queue;

public class ExploreMaxTask extends Task {

    public ExploreMaxTask(Node node) {
        super(node);
    }

    public void run(Queue<Task> todo) {
        node.updateState();
        if (node.hasValue()) {
            node.rollbackState();
            return;
        }
        todo.add(new MaxTask(node));
        for (Node child : node) {
            todo.add(new ExploreMinTask(child));
        }
    }

}
