/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap05.connect4.ai;

import examples.chap05.connect4.Board;
import examples.chap05.connect4.Command;
import java.util.List;
import java.util.Random;

public class HeuristicAI extends AI {
    
    public HeuristicAI(Board board, Random random) {
        super(board, random, "Heuristique");
    }
    
    public Command createCommand() {
        List<Command> list = listCommands();
        if (list.isEmpty())
            return null;
        Command found = null;
        int player = board.getCurrentPlayer();
        for (Command command: list) {
            command.execute(board);
            if (board.isWinner(player)) {
                found = command;
            }
            command.rollback(board);
            if (found != null) {
                return found;
            }
        }
        
        for (Command command: list) {
            command.execute(board);
            List<Command> list2 = listCommands();
            for (Command command2: list2) {
                command2.execute(board);
                if (board.isWinner(-player)) {
                    found = command2;
                }
                command2.rollback(board);
            }
            command.rollback(board);
            if (found != null) {
                return found;
            }
        }
        int index = random.nextInt(list.size());
        return list.get(index);
    }

}
