/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.immutable01.rules.commands;

import examples.chap05.immutable01.state.Characters;
import examples.chap05.immutable01.state.Ghost;
import examples.chap05.immutable01.state.GhostStatus;
import examples.chap05.immutable01.state.MobileElement;
import examples.chap05.immutable01.state.Pacman;
import examples.chap05.immutable01.state.PacmanStatus;
import examples.chap05.immutable01.state.State;
import examples.chap05.immutable01.rules.actions.Action;
import examples.chap05.immutable01.rules.actions.GhostStatusAction;
import examples.chap05.immutable01.rules.actions.PacmanStatusAction;
import examples.chap05.immutable01.rules.actions.UpdateStatusAction;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateStatusCommand implements Command {

    private int charIndex;

    public UpdateStatusCommand(int charIndex) {
        this.charIndex = charIndex;
    }

    public void execute(Queue<Action> actions,State state) {
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
        Action action = new UpdateStatusAction(charIndex);
        action.apply(state);
        actions.add(action);
        if (me.getStatusTime() == 0) {
            if (me instanceof Pacman) {
                Pacman pacman = (Pacman)me;
                if (pacman.getStatus() == PacmanStatus.SUPER) {
                    action = new PacmanStatusAction(PacmanStatus.NORMAL);
                    action.apply(state);
                    actions.add(action);
                }
            }
            else if (me instanceof Ghost) {
                Ghost ghost = (Ghost)me;
                if (ghost.getStatus() == GhostStatus.FLEE) {
                    action = new GhostStatusAction(charIndex,GhostStatus.TRACK);
                    action.apply(state);
                    actions.add(action);
                }
            }
        }
    }
}
