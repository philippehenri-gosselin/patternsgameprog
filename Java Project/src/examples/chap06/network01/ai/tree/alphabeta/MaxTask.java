/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap06.network01.ai.tree.alphabeta;

import examples.chap06.network01.ai.tree.Node;
import java.util.Queue;

public class MaxTask extends Task {

    public MaxTask(Node node) {
        super(node);
    }

    public void run(Queue<Task> todo) {
        node.rollbackState();
        moveMinValueUp();
    }

}
