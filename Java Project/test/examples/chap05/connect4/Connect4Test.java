/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.connect4;

import examples.chap05.connect4.ai.AI;
import examples.chap05.connect4.ai.FirstAI;
import examples.chap05.connect4.ai.HeuristicAI;
import examples.chap05.connect4.ai.RandomAI;
import examples.chap05.connect4.ai.MinimaxAI;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;

public class Connect4Test {

    @Test
    public void testCache() {
        board = new Board();
        int startingPlayer = 1;
        AI ai = new RandomAI(board,new Random());
        for(int repeat=0;repeat<1000;repeat++) {
            board.reset(startingPlayer);
            Queue<Command> commands = Collections.asLifoQueue(new ArrayDeque());
            Queue<Board> boards = Collections.asLifoQueue(new ArrayDeque());
            while(true) {

                assertEquals(board.computeIsWinner(1),board.isWinner(1));
                assertEquals(board.computeIsWinner(-1),board.isWinner(-1));
                
                if (board.isOver()) {
                    break;
                }
                Command command = ai.createCommand();
                if (command == null) {
                    break;
                }
                boards.add(board.clone());
                command.execute(board);
                commands.add(command);
            }
            
            while(!commands.isEmpty()) {
                commands.poll().rollback(board);
                assertEquals(board,boards.poll());
                assertEquals(board.computeIsWinner(1),board.isWinner(1));
                assertEquals(board.computeIsWinner(-1),board.isWinner(-1));
            }
            
            
            startingPlayer = -startingPlayer;
        }
    }
    
    private Board board;
    
    private AI ai1, ai2;
    
    private int wins1, wins2, evens;
    
    public void battle() {
        wins1 = 0;
        wins2 = 0;
        evens = 0;
        int startingPlayer = 1;
        for (int repeat=0;repeat<100;repeat++) {
            board.reset(startingPlayer);
            
            System.out.println("Game "+(repeat+1)+"/100...");

            while(true) {
                if (board.isWinner(1)) {
                    wins1 ++;
                    break;
                }
                if (board.isWinner(-1)) {
                    wins2 ++;
                    break;
                }
                if (board.isOver()) {
                    evens ++;
                    break;
                }
                Command command = null;
                if (board.getCurrentPlayer() == 1) {
                    command = ai1.createCommand();
                }
                else {
                    command = ai2.createCommand();
                }
                if (command == null) {
                    evens ++;
                    break;
                }
                command.execute(board);
            }
            startingPlayer = -startingPlayer;
        }
    }    
    
    public void evalFirstAI() {
        board = new Board();
        Random random = new Random();
        ai1 = new FirstAI(board,random);
        ai2 = new RandomAI(board,random);
        battle();
        System.out.println("Résultats:");
        System.out.println("1: Premier "+wins1+" victoires");
        System.out.println("2: Random "+wins2+" victoires");
        System.out.println(evens+" matchs nuls");
    }
    
    public void evalHeuristicAI() {
        board = new Board();
        Random random = new Random();
        ai1 = new HeuristicAI(board,random);
        ai2 = new RandomAI(board,random);
        battle();
        System.out.println("Résultats:");
        System.out.println("1: Heuristic "+wins1+" victoires");
        System.out.println("2: Random "+wins2+" victoires");
        System.out.println(evens+" matchs nuls");
    }    
    
    public void evalMinimaxAI() {
        board = new Board();
        Random random = new Random();
        ai1 = new MinimaxAI(board,random);
        ai2 = new HeuristicAI(board,random);
        long start = System.currentTimeMillis();
        battle();
        long end = System.currentTimeMillis();
        System.out.println("Résultats:");
        System.out.println("1: Minimax "+wins1+" victoires");
        System.out.println("2: Heuristic "+wins2+" victoires");
        System.out.println(evens+" matchs nuls");
        System.out.println("Temps écoulé (ms): "+(end-start));
    }    
    
    public static void main(String[] args) {
        new Connect4Test().evalFirstAI();
        new Connect4Test().evalHeuristicAI();
        new Connect4Test().evalMinimaxAI();
    }
}
