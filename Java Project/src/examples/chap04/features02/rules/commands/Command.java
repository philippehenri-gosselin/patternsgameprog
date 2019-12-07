/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.features02.rules.commands;

import examples.chap04.features02.state.State;
import examples.chap04.features02.rules.actions.Action;
import java.util.Queue;

public interface Command {

    public void execute(Queue<Action> actions,State state);
    
}
