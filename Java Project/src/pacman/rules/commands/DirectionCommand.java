/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.rules.commands;

import pacman.state.Characters;
import pacman.state.Direction;
import pacman.state.MobileElement;
import pacman.state.Space;
import pacman.state.State;
import pacman.state.World;
import pacman.rules.actions.Action;
import pacman.rules.actions.DirectionAction;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

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
    
    public void toJson(JsonObjectBuilder builder) {
        builder
            .add("type", "Direction")
            .add("charIndex",charIndex)
            .add("direction",direction.toString());
    }
    
    public DirectionCommand(JsonObject json) {
        this.charIndex = json.getInt("charIndex");
        this.direction = Direction.valueOf(json.getString("direction"));
    }

}
