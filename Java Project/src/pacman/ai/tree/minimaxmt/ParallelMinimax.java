/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package pacman.ai.tree.minimaxmt;

import pacman.mt.TaskManager;
import pacman.ai.tree.Node;
import pacman.ai.tree.alphabeta.AlphaBeta;
import java.util.ArrayList;
import java.util.List;

public class ParallelMinimax {
    
    private int parallelDepth = 2;    
    
    public ParallelMinimax(int parallelDepth) {
        this.parallelDepth = parallelDepth;
    }

    public List<Node> run(Node root) 
    {        
        TaskManager manager = TaskManager.getInstance();
        MinimaxProducer producer = new MinimaxProducer(root, parallelDepth);
        try {
            manager.addProducerTask(producer);
            manager.join();
        } catch (InterruptedException ex) {
            throw new RuntimeException();
        }        
        Node precomputedRoot = producer.getPrecomputedRoot();
        AlphaBeta minimax = new AlphaBeta();
        minimax.run(precomputedRoot);
        for (int i=0;i<root.getChildCount();i++) {
            Node child = root.getChild(i);
            Node precomputedChild = precomputedRoot.getChild(i);
            child.setValue(precomputedChild.getValue());
        }
        int max = Integer.MIN_VALUE;
        for(Node child : root) {
            if (!child.hasValue())
                throw new RuntimeException("No value");
            int value = child.getValue();
            if (value > max) {
                max = value;
            }
        }
        ArrayList<Node> bestNodes = new ArrayList();
        for(Node child : root) {
            if (child.getValue() == max) {
                bestNodes.add(child);
            }
        }
        root.setValue(max);            
        return bestNodes;
    }
    
}
