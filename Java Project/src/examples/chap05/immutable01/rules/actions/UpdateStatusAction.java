/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.immutable01.rules.actions;

import examples.chap05.immutable01.state.Characters;
import examples.chap05.immutable01.state.Ghost;
import examples.chap05.immutable01.state.GhostStatus;
import examples.chap05.immutable01.state.MobileElement;
import examples.chap05.immutable01.state.Pacman;
import examples.chap05.immutable01.state.PacmanStatus;
import examples.chap05.immutable01.state.State;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateStatusAction implements Action {

    private int charIndex;

    private MobileElement me;

    public UpdateStatusAction(int charIndex) {
        this.charIndex = charIndex;
    }

    public void apply(State state) {
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

        state.notifyCharacterChanged(charIndex);        
    }

    public void undo(State state) {
        Characters chars = state.getChars();
        MobileElement me = chars.get(charIndex);
        if (me == null) {
            Logger.getLogger("rules").log(Level.SEVERE,"Pas de personnage");
            return;
        }
        int time = me.getStatusTime();
        time ++;
        me.setStatusTime(time);

        state.notifyCharacterChanged(charIndex);      
    }
}
