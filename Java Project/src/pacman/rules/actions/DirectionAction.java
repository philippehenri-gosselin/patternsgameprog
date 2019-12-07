/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.rules.actions;

import pacman.state.Characters;
import pacman.state.Direction;
import pacman.state.MobileElement;
import pacman.state.State;
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
