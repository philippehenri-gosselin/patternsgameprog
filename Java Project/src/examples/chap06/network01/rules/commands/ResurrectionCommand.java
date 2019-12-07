/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.network01.rules.commands;

import examples.chap06.network01.state.Characters;
import examples.chap06.network01.state.Direction;
import examples.chap06.network01.state.Ghost;
import examples.chap06.network01.state.GhostStatus;
import examples.chap06.network01.state.Space;
import examples.chap06.network01.state.SpaceTypeId;
import examples.chap06.network01.state.State;
import examples.chap06.network01.state.StaticElement;
import examples.chap06.network01.state.World;
import examples.chap06.network01.rules.actions.Action;
import examples.chap06.network01.rules.actions.ResurrectionAction;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class ResurrectionCommand implements Command {

    private int charIndex;
    
    public ResurrectionCommand(int charIndex) {
        this.charIndex = charIndex;
    }

    public void execute(Queue<Action> actions,State state) {
        Characters chars = state.getChars();
        Ghost ghost = chars.getGhost(charIndex);
        if (ghost == null) {
            Logger.getLogger("rules").log(Level.SEVERE,"No ghost");
            return;
        }
        if (ghost.getStatus() != GhostStatus.EYES)
            return;
        if (ghost.getPosition() == 0) {
            int x = ghost.getX();
            int y = ghost.getY();
            World world = state.getWorld();
            StaticElement se = world.get(x, y, Direction.NONE);
            if (se instanceof Space) {
                Space space = (Space)se;
                if (space.getSpaceTypeId() == SpaceTypeId.GRAVEYARD) {
                    Action action = new ResurrectionAction(charIndex);
                    action.apply(state);
                    actions.add(action);
                }
            }
        }
    }
    
    public void toJson(JsonObjectBuilder builder) {
        builder
            .add("type", "Resurrection")
            .add("charIndex",charIndex);
    }
    
    public ResurrectionCommand(JsonObject json) {
        this.charIndex = json.getInt("charIndex");
    }
}
