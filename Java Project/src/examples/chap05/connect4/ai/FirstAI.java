/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.connect4.ai;

import examples.chap05.connect4.Board;
import examples.chap05.connect4.Command;
import java.util.List;
import java.util.Random;

public class FirstAI extends AI {

    public FirstAI(Board board, Random random) {
        super(board, random, "Premier");
    }

    public Command createCommand() {
        List<Command> list = listCommands();
        if (list.isEmpty())
            return null;
        return list.get(0);
    }

}
