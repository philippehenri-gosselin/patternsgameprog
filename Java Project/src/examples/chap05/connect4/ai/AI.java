/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap05.connect4.ai;

import examples.chap05.connect4.Board;
import examples.chap05.connect4.Command;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class AI {
    
    protected Board board;
    
    protected Random random;   
    
    protected final String name;
    
    public AI(Board board,Random random,String name) {
        this.board = board;
        this.random = random;
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public List<Command> listCommands() {
        List<Command> commands = new ArrayList();
        if (board.getWinner() == 0) {
            for (int j=0;j<7;j++) {
                if (board.canPlay(j)) {
                    commands.add(new Command(j));
                }
            }
        }
        return commands;
    }
    
    public abstract Command createCommand();

}
