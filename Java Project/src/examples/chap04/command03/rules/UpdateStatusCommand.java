/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.command03.rules;

import examples.chap04.command03.state.Characters;
import examples.chap04.command03.state.Ghost;
import examples.chap04.command03.state.GhostStatus;
import examples.chap04.command03.state.MobileElement;
import examples.chap04.command03.state.Pacman;
import examples.chap04.command03.state.PacmanStatus;
import examples.chap04.command03.state.State;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateStatusCommand implements Command {

    private int charIndex;

    public UpdateStatusCommand(int charIndex) {
        this.charIndex = charIndex;
    }

    public void execute(State state) {
        Characters chars = state.getChars();
        MobileElement me = chars.get(charIndex);
        if (me == null) {
            Logger.getLogger("rules").log(Level.SEVERE,"Pas de personnage");
            return;
        }
        int time = me.getStatusTime();
        if (time <= 0) {
            return;
        }
        time --;
        me.setStatusTime(time);
        if (time == 0) {
            if (me instanceof Pacman) {
                Pacman pacman = (Pacman)me;
                if (pacman.getStatus() == PacmanStatus.SUPER) {
                    pacman.setStatus(PacmanStatus.NORMAL);
                }
            }
            else if (me instanceof Ghost) {
                Ghost ghost = (Ghost)me;
                if (ghost.getStatus() == GhostStatus.FLEE) {
                    ghost.setStatus(GhostStatus.TRACK);
                }
            }
        }
        state.notifyCharacterChanged(charIndex);
    }
}
