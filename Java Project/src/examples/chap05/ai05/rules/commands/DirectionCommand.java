/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.ai05.rules.commands;

import examples.chap05.ai05.state.Characters;
import examples.chap05.ai05.state.Direction;
import examples.chap05.ai05.state.MobileElement;
import examples.chap05.ai05.state.Space;
import examples.chap05.ai05.state.State;
import examples.chap05.ai05.state.World;
import examples.chap05.ai05.rules.actions.Action;
import examples.chap05.ai05.rules.actions.DirectionAction;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DirectionCommand implements Command {

    private int charIndex;

    private Direction direction;

    public DirectionCommand(int charIndex,Direction direction) {
        this.charIndex = charIndex;
        this.direction = direction;
    }
    
    public void execute(Queue<Action> actions,State state) {
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
            Action action = new DirectionAction(charIndex,direction);
            action.apply(state);
            actions.add(action); 
        }
    }
    
    public int getCharIndex() {
        return charIndex;
    }
    
    public Direction getDirection() {
        return direction;
    }

}
