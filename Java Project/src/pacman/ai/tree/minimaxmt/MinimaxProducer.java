/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package pacman.ai.tree.minimaxmt;

import pacman.ai.tree.Node;
import pacman.mt.Task;

public class MinimaxProducer extends Task {

    private final Node root;
    
    private final int parallelDepth;  
    
    private PrecomputedNode precomputedRoot;

    public MinimaxProducer(Node root,int parallelDepth) {
        this.root = root;
        this.parallelDepth = parallelDepth;
    } 
    
    public Node getPrecomputedRoot() {
        return precomputedRoot;
    }
        
    private void explore(Node node,PrecomputedNode precomputedNode) throws InterruptedException {
        if (node.hasValue()) {
            precomputedNode.setValue(node.getValue());
        }
        else if (node.getDepth() >= parallelDepth) {
            addTask( new MinimaxConsumer(precomputedNode,node.createRoot()) );
        }
        else {
            PrecomputedNode[] precomputedChildren = new PrecomputedNode[node.getChildCount()];
            for (int i=0;i<node.getChildCount();i++) {
                Node child = node.getChild(i);
                child.updateState();
                precomputedChildren[i] = new PrecomputedNode(precomputedNode);
                explore(child,precomputedChildren[i]);
                child.rollbackState();
            }
            precomputedNode.setChildren(precomputedChildren);
        }
    }
    
    @Override
    public void run() {
        try {
            precomputedRoot = new PrecomputedNode();
            explore(root,precomputedRoot);
        } catch (InterruptedException ex) {
        }
    }

}
