/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.command02.rules;

import examples.chap04.command02.state.Characters;
import examples.chap04.command02.state.Direction;
import examples.chap04.command02.state.MobileElement;
import examples.chap04.command02.state.Space;
import examples.chap04.command02.state.State;
import examples.chap04.command02.state.World;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DirectionCommand implements Command {

    private int charIndex;

    private Direction direction;

    public DirectionCommand(int charIndex,Direction direction) {
        this.charIndex = charIndex;
        this.direction = direction;
    }
    
    public void execute(State state) {
        Characters chars = state.getChars();
        if (charIndex >= chars.size()) {
            Logger.getLogger("rules").log(Level.WARNING,"No Character "+charIndex);
            return;
        }
        MobileElement me = chars.get(charIndex);
        Direction currentDirection = me.getDirection();        
        
        if (charIndex != 0 && currentDirection.isOppositeOf(direction))
            return;
        
        int pos = me.getPosition();
        int x = me.getX();
        int y = me.getY();
        World world = state.getWorld();
        if ((pos == 0 && world.get(x, y, direction) instanceof Space)
         || currentDirection.isOppositeOf(direction)) {
            me.setDirection(direction);
        }
    }
}
