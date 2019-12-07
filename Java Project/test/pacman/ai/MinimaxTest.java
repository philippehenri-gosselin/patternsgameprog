/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.ai;

import pacman.ai.tree.minimax.Minimax;
import pacman.ai.tree.Node;
import pacman.ai.tree.alphabeta.AlphaBeta;
import pacman.ai.tictactoe.TicTacToe;
import pacman.ai.tictactoe.TicTacToeNode;
import pacman.ai.tree.minimaxmt.ParallelMinimax;
import pacman.mt.TaskManager;
import java.util.List;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;

public class MinimaxTest {

   @Test
    // Configuration où il y a toujours match nul
    public void test1() {
        Random random = new Random();
        for (int repeat=0;repeat<20;repeat++) {
            TicTacToe state = new TicTacToe();
            state.epoch = 0;
            state.cells = new int[][] {{0,0,0},{0,0,0},{0,0,0}};
            int player = (repeat%2)==0?1:-1;
            while(state.epoch < 9) {
                Minimax minimax = new Minimax();
                Node root = new TicTacToeNode(state,player);
                List<Node> bestNodes = minimax.run(root);
                assertFalse(bestNodes.isEmpty());
                TicTacToeNode bestNode = (TicTacToeNode)bestNodes.get(random.nextInt(bestNodes.size()));
                assertFalse(state.hasWon(player));
                assertFalse(state.hasWon(-player));
                state.epoch ++;
                state.cells[bestNode.getX()][bestNode.getY()] = player;
                player = -player;
            }        
        }        
    }
    
    @Test
    // Configuration où on peut toujours gagner
    public void test2() {
        Random random = new Random();
        for (int repeat=0;repeat<20;repeat++) {
            TicTacToe state = new TicTacToe();
            state.epoch = 5;
            state.cells = new int[][] {{1,-1,1},{-1,1,0},{0,0,0}};
            int mainPlayer = 1;
            int player = -1;
            while(state.epoch < 9) {
                Minimax minimax = new Minimax();
                TicTacToeNode root = new TicTacToeNode(state,player);
                List<Node> bestNodes = minimax.run(root);
                assertFalse(bestNodes.isEmpty());
                TicTacToeNode bestNode = (TicTacToeNode)bestNodes.get(random.nextInt(bestNodes.size()));
                assertNotNull(bestNode);
                if (player == mainPlayer) {
                    assertTrue(bestNode.getValue() > 0);
                }
                else {
                    assertTrue(bestNode.getValue() < 0);
                }
                state.epoch ++;
                state.cells[bestNode.getX()][bestNode.getY()] = player;
                assertFalse(state.hasWon(-mainPlayer));
                if (state.hasWon(mainPlayer)) {
                    break;
                }
                player = -player;
            }        
            assertTrue(state.hasWon(mainPlayer));
        }
    }
    
    @Test
    // Comparaison minimax et alpha beta
    public void test3() {
        Random random = new Random();

        for (int repeat=0;repeat<100;repeat++) {
            TicTacToe state = new TicTacToe();
            state.cells = new int[][] {{0,0,0},{0,0,0},{0,0,0}};
            state.epoch = 1+random.nextInt(5);
            for(int e=0;e<state.epoch;e++) {
                int x = 0;
                int y = 0;
                while(true) {
                    x = random.nextInt(3);
                    y = random.nextInt(3);
                    if (state.cells[x][y] == 0)
                        break;
                }
                state.cells[x][y] = 1 - 2*(e%2);
            }

            int player = (repeat%2)==0?1:-1;
            while(state.epoch < 9) {
                Minimax minimax = new Minimax();
                TicTacToeNode root = new TicTacToeNode(state,player);
                List<Node> bestNodes = minimax.run(root);
                assertFalse(bestNodes.isEmpty());
                TicTacToeNode bestNode = (TicTacToeNode)bestNodes.get(random.nextInt(bestNodes.size()));
                assertNotNull(bestNode);

                AlphaBeta alphabeta = new AlphaBeta();
                TicTacToeNode root2 = new TicTacToeNode(state,player);
                alphabeta.run(root2);
                int[][] scores1 = root.getScores();
                int[][] scores2 = root2.getScores();
                assertArrayEquals(scores1, scores2);

                if (state.hasWon(player) || state.hasWon(-player))
                    break;                
                state.epoch ++;
                state.cells[bestNode.getX()][bestNode.getY()] = player;
                player = -player;
            }
        }
    }
    
    @Test
    // Comparaison minimax et parallelminimax
    public void test4() {
        Random random = new Random();
        TaskManager.getInstance().launch(); 
        for (int repeat=0;repeat<100;repeat++) {
            TicTacToe state = new TicTacToe();
            state.cells = new int[][] {{0,0,0},{0,0,0},{0,0,0}};
            state.epoch = 1+random.nextInt(5);
            for(int e=0;e<state.epoch;e++) {
                int x = 0;
                int y = 0;
                while(true) {
                    x = random.nextInt(3);
                    y = random.nextInt(3);
                    if (state.cells[x][y] == 0)
                        break;
                }
                state.cells[x][y] = 1 - 2*(e%2);
            }

            int player = (repeat%2)==0?1:-1;
            while(state.epoch < 9) {
                Minimax minimax = new Minimax();
                TicTacToeNode root = new TicTacToeNode(state,player);
                List<Node> bestNodes = minimax.run(root);
                assertFalse(bestNodes.isEmpty());
                TicTacToeNode bestNode = (TicTacToeNode)bestNodes.get(random.nextInt(bestNodes.size()));
                assertNotNull(bestNode);

                ParallelMinimax parallelMinimax = new ParallelMinimax(2);
                TicTacToeNode root2 = new TicTacToeNode(state,player);
                parallelMinimax.run(root2);
                int[][] scores1 = root.getScores();
                int[][] scores2 = root2.getScores();
                assertArrayEquals(scores1, scores2);

                if (state.hasWon(player) || state.hasWon(-player))
                    break;                
                state.epoch ++;
                state.cells[bestNode.getX()][bestNode.getY()] = player;
                player = -player;
            }
        }
        TaskManager.getInstance().terminate();
    }
    
}
