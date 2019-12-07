/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.features03.rules.actions;

import examples.chap04.features03.state.Characters;
import examples.chap04.features03.state.Direction;
import examples.chap04.features03.state.MobileElement;
import examples.chap04.features03.state.State;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DirectionAction implements Action {

    private int charIndex;
    
    private Direction prevdir;

    private Direction newdir;

    public DirectionAction(int charIndex,Direction newdir) {
        this.charIndex = charIndex;
        this.newdir = newdir;
    }

    public void apply(State state) {
        Characters chars = state.getChars();
        if (charIndex >= chars.size()) {
            Logger.getLogger("rules").log(Level.WARNING,"No Character "+charIndex);
            return;
        }
        MobileElement me = chars.get(charIndex);
        prevdir = me.getDirection();        
        me.setDirection(newdir);
    }

    public void undo(State state) {
        Characters chars = state.getChars();
        if (charIndex >= chars.size()) {
            Logger.getLogger("rules").log(Level.WARNING,"No Character "+charIndex);
            return;
        }
        MobileElement me = chars.get(charIndex);
        me.setDirection(prevdir);
    }
}
