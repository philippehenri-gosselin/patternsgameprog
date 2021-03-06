/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.network01.ai;

import examples.chap06.network01.rules.commands.Command;
import examples.chap06.network01.state.State;
import java.util.List;
import java.util.Random;

public class RandomAI extends AI {

    public RandomAI(State state, int charIndex, CommandsLister commandsLister, Random random) {
        super(state, charIndex, commandsLister, random);
    }

    public Command createCommand() {
        List<Command> list = commandsLister.listCommands(state, charIndex);
        if (list.isEmpty())
            return null;
        int index = random.nextInt(list.size());
        return list.get(index);
    }
}
