/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.treevisitor.iterative;

import examples.chap05.treevisitor.TreeVisitor;
import java.util.Queue;
import pacman.ai.tree.Node;

public class VisitTask extends Task {

    private TreeVisitor visitor;

    public VisitTask(Node node, TreeVisitor visitor) {
        super(node);
        this.visitor = visitor;
    }

    public void execute(Queue<Task> queue) {
        visitor.visit(node);
        for (int i=node.getChildCount()-1;i>=0;i--) {
            Node child = node.getChild(i);
            queue.add(new RollbackTask(child));
            queue.add(new VisitTask(child, visitor));
            queue.add(new UpdateTask(child));
        }
    }
}
