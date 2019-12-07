/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap06.threads02.ai.tree.alphabeta;

import examples.chap06.threads02.ai.tree.Node;
import java.util.Queue;

public abstract class Task {

    protected final Node node;
    
    public Task(Node node) {
        this.node = node;
    }
    
    public Node getNode() {
        return node;
    }
    
    protected void moveMaxValueUp() {
        Node parent = node.getParent();
        if (parent != null) {
            if (!parent.hasValue()
              || parent.getValue() < node.getValue()) {
                parent.setValue(node.getValue());
            }
        }
    }

    protected void moveMinValueUp() {
        Node parent = node.getParent();
        if (parent != null) {
            if (!parent.hasValue()
              || parent.getValue() > node.getValue()) {
                parent.setValue(node.getValue());
            }
        }
    }
    
    public abstract void run(Queue<Task> todo);
    
}
