/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.network01.rules.actions;

import examples.chap06.network01.state.Characters;
import examples.chap06.network01.state.Ghost;
import examples.chap06.network01.state.GhostStatus;
import examples.chap06.network01.state.State;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KillGhostAction implements Action {

    private int charIndex;

    private Ghost ghost;

    public KillGhostAction(int charIndex) {
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
        ghost.setStatus(GhostStatus.EYES);
        ghost.setStatusTime(0);
        state.notifyCharacterChanged(charIndex);
    }

    public void undo(State state) {
        Characters chars = state.getChars();
        chars.set(charIndex, (Ghost)ghost.clone());
    }
}
