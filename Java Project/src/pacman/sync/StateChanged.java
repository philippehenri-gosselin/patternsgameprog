/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.sync;

import pacman.state.State;
import pacman.state.StateObserver;

public class StateChanged extends Event {

    public StateChanged(State state) {
        super(state);
    }

    public void dispatchEvent(StateObserver observer) {
        observer.stateChanged(state);
    }
}
