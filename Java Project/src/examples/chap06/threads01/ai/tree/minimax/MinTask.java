/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.threads01.ai.tree.minimax;

import examples.chap06.threads01.ai.tree.Node;
import java.util.Queue;

public class MinTask extends Task {

    public MinTask(Node node) {
        super(node);
    }

    public void run(Queue<Task> todo) {
        int min = Integer.MAX_VALUE;
        for(Node child : node) {
            if (!child.hasValue())
                throw new RuntimeException("No value");
            int value = child.getValue();
            if (value < min) {
                min = value;
            }
        }
        node.setValue(min);
        node.rollbackState();
    }

}
