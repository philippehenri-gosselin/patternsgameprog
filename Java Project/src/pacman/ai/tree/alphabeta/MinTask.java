/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package pacman.ai.tree.alphabeta;

import pacman.ai.tree.Node;
import java.util.Queue;

public class MinTask extends Task {

    public MinTask(Node node) {
        super(node);
    }

    public void run(Queue<Task> todo) {
        node.rollbackState();
        moveMaxValueUp();
    }

}
