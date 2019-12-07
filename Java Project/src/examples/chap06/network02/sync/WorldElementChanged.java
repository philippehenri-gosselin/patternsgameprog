/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.network02.sync;

import examples.chap06.network02.state.State;
import examples.chap06.network02.state.StateObserver;

public class WorldElementChanged extends Event {

    private final int x;

    private final int y;

    public WorldElementChanged(State state,int x,int y) {
        super(state);
        this.x = x;
        this.y = y;
    }

    public void dispatchEvent(StateObserver observer) {
        observer.worldElementChanged(state,x,y);
    }
}
