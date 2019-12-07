/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.command03.rules;

import examples.chap04.command03.state.Characters;
import examples.chap04.command03.state.Direction;
import examples.chap04.command03.state.MobileElement;
import examples.chap04.command03.state.Pacman;
import examples.chap04.command03.state.PacmanStatus;
import examples.chap04.command03.state.Space;
import examples.chap04.command03.state.State;
import examples.chap04.command03.state.World;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MoveCommand implements Command {

    private int charIndex;
    
    public MoveCommand(int charIndex) {
        this.charIndex = charIndex;
    }

    public void execute(State state) {
        World world = state.getWorld();
        Characters chars = state.getChars();
        if (charIndex >= chars.size()) {
            Logger.getLogger("rules").log(Level.WARNING,"No Character "+charIndex);
            return;
        }
        MobileElement me = chars.get(charIndex);
        
        if (me instanceof Pacman) {
            Pacman pacman = (Pacman)me;
            if (pacman.getStatus() == PacmanStatus.DEAD) {     
                state.notifyCharacterChanged(charIndex);
                return;
            }
        }
        
        int pos = me.getPosition();
        int x = me.getX();
        int y = me.getY();
        Direction direction = me.getDirection();

        if (pos == 0) {
            if (!(world.get(x, y, direction) instanceof Space)) {
                state.notifyCharacterChanged(charIndex);
                return;
            }
        }
        
        switch(direction) {
            case EAST:
                pos ++;
                if (pos == me.getSpeed()) {
                    pos = -me.getSpeed();
                    x ++;
                    if (x >= world.getWidth())
                        x = 0;
                }
                break;                    
            case WEST:
                pos --;
                if (pos == -me.getSpeed()-1) {
                    pos = me.getSpeed()-1;
                    x --;
                    if (x < 0)
                        x = world.getWidth()-1;
                }
                break;                    
            case SOUTH:
                pos ++;
                if (pos == me.getSpeed()) {
                    pos = -me.getSpeed();
                    y ++;
                    if (y >= world.getHeight())
                        y = 0;
                }
                break;                    
            case NORTH:
                pos --;
                if (pos == -me.getSpeed()-1) {
                    pos = me.getSpeed()-1;
                    y --;
                    if (y < 0)
                        y = world.getHeight()-1;
                }
                break;                    
        }
        me.setPosition(pos);
        me.setX(x);
        me.setY(y);
        state.notifyCharacterChanged(charIndex);
    }
}
