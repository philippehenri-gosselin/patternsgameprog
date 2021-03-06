/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

    package examples.chap05.immutable02.rules.actions;

import examples.chap05.immutable02.state.Characters;
import examples.chap05.immutable02.state.Ghost;
import examples.chap05.immutable02.state.GhostStatus;
import examples.chap05.immutable02.state.State;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResurrectionAction implements Action {
    
    private int charIndex;

    private Ghost ghost;
    
    public ResurrectionAction(int charIndex) {
        this.charIndex = charIndex;
    }
    
    public void apply(State state) {
        Characters chars = state.getChars();
        Ghost ghost = chars.getGhost(charIndex);
        if (ghost == null) {
            Logger.getLogger("rules").log(Level.SEVERE,"No ghost");
            return;
        }
        this.ghost = (Ghost)ghost.clone();
        ghost.setStatus(GhostStatus.TRACK);
        ghost.setStatusTime(0);
        state.notifyCharacterChanged(charIndex);
    }

    public void undo(State state) {
        Characters chars = state.getChars();
        chars.set(charIndex, (Ghost)ghost.clone());
    }
}
