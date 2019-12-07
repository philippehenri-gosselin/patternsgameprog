/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.sync;

import pacman.state.State;
import pacman.state.StateObserver;

public abstract class Event {

    protected final State state;

    public Event(State state) {
        this.state = state;
    }

    public abstract void dispatchEvent(StateObserver observer);
}
