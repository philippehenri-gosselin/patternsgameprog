/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.rules.commands;

import pacman.rules.actions.Action;
import pacman.rules.actions.EnableSuperAction;
import pacman.rules.actions.GumAction;
import pacman.state.Characters;
import pacman.state.Direction;
import pacman.state.Pacman;
import pacman.state.PacmanStatus;
import pacman.state.Space;
import pacman.state.SpaceTypeId;
import pacman.state.State;
import pacman.state.StaticElement;
import pacman.state.World;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class GumsCommand implements Command {
    
    public GumsCommand() {
        
    }

    public void execute(Queue<Action> actions,State state) {
        Characters chars = state.getChars();
        Pacman pacman = chars.getPacman();
        if (pacman == null) {
            Logger.getLogger("rules").log(Level.SEVERE,"No Pacman");
            return;
        }
        if (pacman.getStatus() == PacmanStatus.DEAD)
            return;
        if (pacman.getPosition() == 0) {
            int x = pacman.getX();
            int y = pacman.getY();
            World world = state.getWorld();
            StaticElement se = world.get(x, y, Direction.NONE);
            if (se instanceof Space) {
                Space space = (Space)se;
                SpaceTypeId type = space.getSpaceTypeId();
                if (type == SpaceTypeId.GUM
                 || type == SpaceTypeId.SUPERGUM) {
                    Action action = new GumAction(x,y,type);
                    action.apply(state);
                    actions.add(action);
                    if (type == SpaceTypeId.SUPERGUM) {
                        action = new EnableSuperAction(state.getSuperDuration());
                        action.apply(state);
                        actions.add(action);
                    }
                }
            }
        }        
    }
    
    public void toJson(JsonObjectBuilder builder) {
        builder
            .add("type", "Gums");
    }
    
    public GumsCommand(JsonObject json) {
    }
}
