/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.command03.rules;

import examples.chap04.command03.state.Characters;
import examples.chap04.command03.state.Ghost;
import examples.chap04.command03.state.GhostStatus;
import examples.chap04.command03.state.Pacman;
import examples.chap04.command03.state.PacmanStatus;
import examples.chap04.command03.state.State;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CollisionsCommand implements Command {

    public void killGhost(State state, int charIndex) {
        Characters chars = state.getChars();
        Ghost ghost = chars.getGhost(charIndex);
        if (ghost == null) {
            Logger.getLogger("rules").log(Level.SEVERE,"No ghost");
            return;
        }
        ghost.setStatus(GhostStatus.EYES);
        ghost.setStatusTime(0);
        state.notifyCharacterChanged(charIndex);
    }

    public void killPacman(State state) {
        Characters chars = state.getChars();
        Pacman pacman = chars.getPacman();
        if (pacman == null) {
            Logger.getLogger("rules").log(Level.SEVERE,"No Pacman");
            return;
        }
        pacman.setStatus(PacmanStatus.DEAD);
        pacman.setStatusTime(12);
        state.notifyCharacterChanged(0);
    }

    public void execute(State state) {
        Characters chars = state.getChars();
        Pacman pacman = chars.getPacman();
        if (pacman == null) {
            Logger.getLogger("rules").log(Level.SEVERE,"No Pacman");
            return;
        }
        if (pacman.getStatus() == PacmanStatus.DEAD)
            return;
        int px = pacman.getX();
        int py = pacman.getY();
        for (int index=1;index<chars.size();index++) {
            Ghost ghost = chars.getGhost(index);
            if (ghost == null) {
                Logger.getLogger("rules").log(Level.SEVERE,"No ghost");
                continue;
            }
            int gx = ghost.getX();
            int gy = ghost.getY();
            if (gx != px || gy != py)
                continue;
            if (ghost.getStatus() == GhostStatus.EYES)
                continue;
            if (ghost.getStatus() == GhostStatus.FLEE) {
                killGhost(state,index);
            }
            else if (ghost.getStatus() == GhostStatus.TRACK) {
                killPacman(state);
            }            
        }
    }
}
