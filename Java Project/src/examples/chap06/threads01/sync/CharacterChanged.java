/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.threads01.sync;

import examples.chap06.threads01.state.State;
import examples.chap06.threads01.state.StateObserver;

public class CharacterChanged extends Event {

    private final int charIndex;

    public CharacterChanged(State state,int charIndex) {
        super(state);
        this.charIndex = charIndex;
    }

    public void dispatchEvent(StateObserver observer) {
        observer.characterChanged(state,charIndex);
    }
}
