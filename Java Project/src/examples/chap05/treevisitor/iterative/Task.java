/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.treevisitor.iterative;

import java.util.Queue;
import pacman.ai.tree.Node;

public abstract class Task {

    protected final Node node;
    
    public Task(Node node) {
        this.node = node;
    }
    
    public abstract void execute(Queue<Task> queue);
}
