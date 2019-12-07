/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.ai06.ai.tree;

import java.util.Iterator;

public class NodeIterator implements Iterator<Node> {

    private final Node node;

    private int i = -1;

    public NodeIterator(Node node) {
        this.node = node;
    }

    @Override
    public boolean hasNext() {
        i ++;
        return i < node.getChildCount();
    }

    @Override
    public Node next() {
        return node.getChild(i);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        
}
