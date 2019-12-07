/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.ai04.ai;

import examples.chap05.ai04.rules.commands.Command;
import examples.chap05.ai04.state.State;
import java.util.List;

public interface CommandsLister {

    public List<Command> listCommands(State state, int charIndex);
}
