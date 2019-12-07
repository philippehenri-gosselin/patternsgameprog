/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.ai06.ai.tree;

public abstract class Node implements Iterable<Node> {

    protected final Node parent;
    
    protected final int depth;
    
    protected Node[] children;
    
    private boolean hasValue = false;

    private int value;
    
    public Node(Node parent) {
        this.parent = parent;
        this.depth = (parent!=null)?(parent.depth+1):0;
    }
    
    public Node getParent() {
        return parent;
    }
    
    public int getDepth() {
        return depth;
    }

    public boolean hasValue() {
        return hasValue;
    }

    public int getValue() {
        if (!hasValue)
            throw new RuntimeException("No value");
        return value;
    }

    public void setValue(int value) {
        hasValue = true;
        this.value = value;
    }

    public int getChildCount() {
        if (children == null) {
            createChildren();
        }
        return children.length;
    }

    public Node getChild(int i) {
        if (children == null) {
            createChildren();
        }
        return children[i];
    }

    public NodeIterator iterator() {
        if (children == null) {
            createChildren();
        }
        return new NodeIterator(this);
    }

    public abstract void createChildren();
    
    public abstract void updateState();
    
    public abstract void rollbackState();

}
