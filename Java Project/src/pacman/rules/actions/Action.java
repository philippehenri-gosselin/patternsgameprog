/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.rules.actions;

import pacman.state.State;

public interface Action {

    public void apply(State state);

    public void undo(State state);
}
