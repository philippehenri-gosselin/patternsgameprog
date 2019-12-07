/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.sync;

import pacman.state.State;
import pacman.state.StateObserver;

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
