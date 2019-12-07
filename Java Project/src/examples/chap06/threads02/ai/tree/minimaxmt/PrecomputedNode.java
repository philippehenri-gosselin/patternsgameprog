/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap06.threads02.ai.tree.minimaxmt;

import examples.chap06.threads02.ai.tree.Node;

public class PrecomputedNode extends Node {

    public PrecomputedNode() {
        super(null);
    }
    
    public PrecomputedNode(Node parent) {
        super(parent);
    }
    
    public PrecomputedNode(Node parent,Node[] children) {
        super(parent);
        this.children = children;
    }

    public PrecomputedNode(Node parent,int value) {
        super(parent);
        setValue(value);
    }
    
    public void setChildren(PrecomputedNode[] children) {
        this.children = children;
    }
    
    @Override
    public void createChildren() {
    }

    @Override
    public void updateState() {
    }

    @Override
    public void rollbackState() {
    }

    @Override
    public Node createRoot() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
