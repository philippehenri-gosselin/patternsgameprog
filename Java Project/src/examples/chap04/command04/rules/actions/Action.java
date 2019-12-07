/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.command04.rules.actions;

import examples.chap04.command04.state.State;

public interface Action {

    public void apply(State state);

    public void undo(State state);
}
