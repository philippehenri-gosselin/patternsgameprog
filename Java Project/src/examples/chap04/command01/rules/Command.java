/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.command01.rules;

import examples.chap04.command01.state.State;

public interface Command {

    public void execute(State state);
}
