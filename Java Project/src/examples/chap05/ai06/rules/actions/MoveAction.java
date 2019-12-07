/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.ai06.rules.actions;

import examples.chap05.ai06.state.Characters;
import examples.chap05.ai06.state.MobileElement;
import examples.chap05.ai06.state.State;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MoveAction implements Action {

    private int charIndex;

    private int dx;

    private int dy;

    private int dpos;

    public MoveAction(int charIndex, int dx, int dy, int dpos) {
        this.charIndex = charIndex;
        this.dx = dx;
        this.dy = dy;
        this.dpos = dpos;
    }

    public void apply(State state) {
        Characters chars = state.getChars();
        if (charIndex >= chars.size()) {
            Logger.getLogger("rules").log(Level.WARNING,"No Character "+charIndex);
            return;
        }
        MobileElement me = chars.get(charIndex);
        if (me == null) {
            Logger.getLogger("rules").log(Level.WARNING,"No Character "+charIndex);
            return;
        }
        me.setPosition(me.getPosition() + dpos);
        me.setX(me.getX() + dx);
        me.setY(me.getY() + dy);
        state.notifyCharacterChanged(charIndex);
    }

    public void undo(State state) {
        Characters chars = state.getChars();
        if (charIndex >= chars.size()) {
            Logger.getLogger("rules").log(Level.WARNING,"No Character "+charIndex);
            return;
        }
        MobileElement me = chars.get(charIndex);
        if (me == null) {
            Logger.getLogger("rules").log(Level.WARNING,"No Character "+charIndex);
            return;
        }
        me.setPosition(me.getPosition() - dpos);
        me.setX(me.getX() - dx);
        me.setY(me.getY() - dy);
        state.notifyCharacterChanged(charIndex);
    }
}
