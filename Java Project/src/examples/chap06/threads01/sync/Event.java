/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.threads01.sync;

import examples.chap06.threads01.state.State;
import examples.chap06.threads01.state.StateObserver;

public abstract class Event {

    protected final State state;

    public Event(State state) {
        this.state = state;
    }

    public abstract void dispatchEvent(StateObserver observer);
}
