/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.threads01.rules.commands;

import examples.chap06.threads01.rules.actions.Action;
import examples.chap06.threads01.state.State;
import java.util.Queue;

public interface Command {

    public void execute(Queue<Action> actions,State state);
    
}
