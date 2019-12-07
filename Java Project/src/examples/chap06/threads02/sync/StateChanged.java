/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.threads02.sync;

import examples.chap06.threads02.state.State;
import examples.chap06.threads02.state.StateObserver;

public class StateChanged extends Event {

    public StateChanged(State state) {
        super(state);
    }

    public void dispatchEvent(StateObserver observer) {
        observer.stateChanged(state);
    }
}
