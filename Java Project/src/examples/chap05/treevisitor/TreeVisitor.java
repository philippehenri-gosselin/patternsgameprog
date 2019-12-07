/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.treevisitor;

import pacman.ai.tree.Node;

public interface TreeVisitor {

    public void visit(Node node);
}
