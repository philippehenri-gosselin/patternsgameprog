/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.rules.actions;

import pacman.state.Characters;
import pacman.state.Pacman;
import pacman.state.PacmanStatus;
import pacman.state.State;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PacmanStatusAction implements Action {

    private PacmanStatus newStatus;

    private PacmanStatus prevStatus;

    public PacmanStatusAction(PacmanStatus newStatus) {
        this.newStatus = newStatus;
    }

    public void apply(State state) {
        Characters chars = state.getChars();
        Pacman pacman = chars.getPacman();
        if (pacman == null) {
            Logger.getLogger("rules").log(Level.SEVERE,"No Pacman");
            return;
        }
        prevStatus = pacman.getStatus();
        pacman.setStatus(newStatus);
        state.notifyCharacterChanged(0);              
    }

    public void undo(State state) {
        Characters chars = state.getChars();
        Pacman pacman = chars.getPacman();
        if (pacman == null) {
            Logger.getLogger("rules").log(Level.SEVERE,"No Pacman");
            return;
        }
        pacman.setStatus(prevStatus);
        state.notifyCharacterChanged(0);   
    }
}