/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.treevisitor;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import pacman.ai.tictactoe.TicTacToe;
import pacman.ai.tictactoe.TicTacToeNode;
import pacman.ai.tree.Node;

public class TreeVisitorTest {
  
    private int count;
    
    private ArrayList<TicTacToe> record = new ArrayList();
    
    @Test
    public void test() {
    
        TicTacToe state = new TicTacToe();
        state.epoch = 6;
        state.cells = new int[][] {{1,-1,0},{-1,1,0},{1,-1,0}};
        Node node = new TicTacToeNode(state,-1);

        TreeTraversal traversal = new RecursiveTraversal(node);
        traversal.accept(new TreeVisitor() {
            public void visit(Node node) {
                TicTacToeNode tttNode = (TicTacToeNode)node;
                TicTacToe state = tttNode.getState();
                count ++;
                record.add(state.clone());
            }
        });
        assertEquals(16,count);

        count = 0;
        traversal = new IterativeTraversal(node);
        traversal.accept(new TreeVisitor() {
            public void visit(Node node) {
                TicTacToeNode tttNode = (TicTacToeNode)node;
                TicTacToe state = tttNode.getState();
                assertEquals(record.get(count),state);
                count ++;
            }
        });
        assertEquals(16,count);
        
    }
    
}
