/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.ai03.rules.actions;

import examples.chap05.ai03.state.Characters;
import examples.chap05.ai03.state.Ghost;
import examples.chap05.ai03.state.GhostStatus;
import examples.chap05.ai03.state.State;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GhostStatusAction implements Action {

    private int charIndex;
    
    private GhostStatus newStatus;

    private GhostStatus prevStatus;

    public GhostStatusAction(int charIndex,GhostStatus newStatus) {
        this.charIndex = charIndex;
        this.newStatus = newStatus;
    }

    public void apply(State state) {
        Characters chars = state.getChars();
        Ghost ghost = chars.getGhost(charIndex);
        if (ghost == null) {
            Logger.getLogger("rules").log(Level.SEVERE,"No ghost");
            return;
        }
        prevStatus = ghost.getStatus();
        ghost.setStatus(newStatus);
        state.notifyCharacterChanged(charIndex);        
    }

    public void undo(State state) {
        Characters chars = state.getChars();
        Ghost ghost = chars.getGhost(charIndex);
        if (ghost == null) {
            Logger.getLogger("rules").log(Level.SEVERE,"No ghost");
            return;
        }
        ghost.setStatus(prevStatus);
        state.notifyCharacterChanged(charIndex);        
    }
}
