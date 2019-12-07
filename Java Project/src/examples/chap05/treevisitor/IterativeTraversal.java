/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.treevisitor;

import examples.chap05.treevisitor.iterative.*;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;
import pacman.ai.tree.Node;

public class IterativeTraversal implements TreeTraversal {

    private final Node root;

    public IterativeTraversal(Node root) {
        this.root = root;
    }
    
    public void accept(TreeVisitor visitor) {
        Queue<Task> queue = Collections.asLifoQueue(new ArrayDeque());
        queue.add(new VisitTask(root,visitor));
        while(!queue.isEmpty()) {
            Task task = queue.poll();
            task.execute(queue);
        }
    }
     
}
