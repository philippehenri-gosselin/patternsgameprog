/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.ai;

import pacman.rules.commands.Command;
import pacman.state.State;
import java.util.List;

public interface CommandsLister {

    public List<Command> listCommands(State state, int charIndex);
}
