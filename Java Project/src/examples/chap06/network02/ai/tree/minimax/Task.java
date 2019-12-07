/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.network02.ai.tree.minimax;

import examples.chap06.network02.ai.tree.Node;
import java.util.Queue;

public abstract class Task {

    protected final Node node;
    
    public Task(Node node) {
        this.node = node;
    }
    
    public Node getNode() {
        return node;
    }

    public abstract void run(Queue<Task> todo);
    
}
