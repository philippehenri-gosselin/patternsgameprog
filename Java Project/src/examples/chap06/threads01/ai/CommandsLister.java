/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.threads01.ai;

import examples.chap06.threads01.rules.commands.Command;
import examples.chap06.threads01.state.State;
import java.util.List;

public interface CommandsLister {

    public List<Command> listCommands(State state, int charIndex);
}
