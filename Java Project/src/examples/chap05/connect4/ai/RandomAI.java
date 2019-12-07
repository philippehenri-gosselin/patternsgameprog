/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap05.connect4.ai;

import examples.chap05.connect4.Board;
import examples.chap05.connect4.Command;
import java.util.List;
import java.util.Random;

public class RandomAI extends AI {

    public RandomAI(Board board, Random random) {
        super(board, random, "Aléatoire");
    }

    public Command createCommand() {
        List<Command> list = listCommands();
        if (list.isEmpty())
            return null;
        int index = random.nextInt(list.size());
        return list.get(index);
    }

}
