/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.rules.commands;

import pacman.state.Characters;
import pacman.state.Direction;
import pacman.state.MobileElement;
import pacman.state.Pacman;
import pacman.state.PacmanStatus;
import pacman.state.Space;
import pacman.state.State;
import pacman.state.World;
import pacman.rules.actions.Action;
import pacman.rules.actions.MoveAction;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class MoveCommand implements Command {

    private int charIndex;
    
    public MoveCommand(int charIndex) {
        this.charIndex = charIndex;
    }

    public void execute(Queue<Action> actions,State state) {
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
                Action action = new MoveAction(charIndex,0,0,0);
                action.apply(state);
                actions.add(action);
                return;
            }
        }
        
        int pos = me.getPosition();
        int x = me.getX();
        int y = me.getY();
        Direction direction = me.getDirection();

        if (pos == 0) {
            if (!(world.get(x, y, direction) instanceof Space)) {
                Action action = new MoveAction(charIndex,0,0,0);
                action.apply(state);
                actions.add(action);
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
        int dx = x - me.getX();
        int dy = y - me.getY();
        int dpos = pos - me.getPosition();
        Action action = new MoveAction(charIndex,dx,dy,dpos);
        action.apply(state);
        actions.add(action);
    }

    public void toJson(JsonObjectBuilder builder) {
        builder
            .add("type", "Move")
            .add("charIndex",charIndex);
    }
    
    public MoveCommand(JsonObject json) {
        this.charIndex = json.getInt("charIndex");
    }
}
