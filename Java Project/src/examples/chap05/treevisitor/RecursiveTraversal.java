/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.treevisitor;

import pacman.ai.tree.Node;

public class RecursiveTraversal implements TreeTraversal {

    private final Node root;

    public RecursiveTraversal(Node root) {
        this.root = root;
    }
    
    private void accept(TreeVisitor visitor,Node node) {
        if (node == null) {
            return;
        }
        node.updateState();
        visitor.visit(node);
        for (Node child : node) {
            accept(visitor,child);
        }
        node.rollbackState();
    }
    
    public void accept(TreeVisitor visitor) {
        visitor.visit(root);
        for (Node child : root) {
            accept(visitor,child);
        }
    }

}
