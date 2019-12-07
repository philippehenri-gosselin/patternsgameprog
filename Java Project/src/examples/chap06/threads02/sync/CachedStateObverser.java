/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap06.threads02.sync;

import examples.chap06.threads02.state.State;
import examples.chap06.threads02.state.StateObserver;
import java.util.ArrayList;
import java.util.List;

public class CachedStateObverser implements StateObserver {

    private List<Event> events = new ArrayList();
    
    public void dispatchEvents(StateObserver observer) {
        for (Event event : events) {
            event.dispatchEvent(observer);
        }
    }    
    
    public void clearEvents() {
        events.clear();
    }
    
    public void stateChanged(State state) {
        events.add(new StateChanged(state));
    }

    public void worldElementChanged(State state, int x, int y) {
        events.add(new WorldElementChanged(state,x,y));
    }
    
    public void characterChanged(State state, int charIndex) {
        events.add(new CharacterChanged(state,charIndex));
    }

}
