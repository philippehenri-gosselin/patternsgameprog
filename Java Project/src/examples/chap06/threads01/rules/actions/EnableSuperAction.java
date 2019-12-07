/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.threads01.rules.actions;

import examples.chap06.threads01.state.Characters;
import examples.chap06.threads01.state.Ghost;
import examples.chap06.threads01.state.GhostStatus;
import examples.chap06.threads01.state.Pacman;
import examples.chap06.threads01.state.PacmanStatus;
import examples.chap06.threads01.state.State;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EnableSuperAction implements Action {

    private int duration;

    private Characters chars;

    public EnableSuperAction(int duration) {
        this.duration = duration;
    }

    public void apply(State state) {
        Characters chars = state.getChars();
        this.chars = chars.clone();
        Pacman pacman = chars.getPacman();
        if (pacman == null) {
            Logger.getLogger("rules").log(Level.SEVERE,"No Pacman");
            return;
        }
        pacman.setStatus(PacmanStatus.SUPER);
        pacman.setStatusTime(duration);
        state.notifyCharacterChanged(0);
        
        for (int index=1;index<chars.size();index++) {
            Ghost ghost = chars.getGhost(index);
            if (ghost == null) {
                Logger.getLogger("rules").log(Level.SEVERE,"No ghost");
                continue;
            }
            ghost.setStatusTime(state.getSuperDuration());
            if (ghost.getStatus() != GhostStatus.EYES) {
                ghost.setStatus(GhostStatus.FLEE);
            }
            state.notifyCharacterChanged(index);
        }        
    }

    public void undo(State state) {
        state.setChars(chars.clone());
        for (int index=0;index<chars.size();index++) {
            state.notifyCharacterChanged(index);
        }
    }
}
