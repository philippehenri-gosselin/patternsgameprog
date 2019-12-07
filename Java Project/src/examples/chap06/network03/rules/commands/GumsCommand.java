/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.network03.rules.commands;

import examples.chap06.network03.rules.actions.Action;
import examples.chap06.network03.rules.actions.EnableSuperAction;
import examples.chap06.network03.rules.actions.GumAction;
import examples.chap06.network03.state.Characters;
import examples.chap06.network03.state.Direction;
import examples.chap06.network03.state.Pacman;
import examples.chap06.network03.state.PacmanStatus;
import examples.chap06.network03.state.Space;
import examples.chap06.network03.state.SpaceTypeId;
import examples.chap06.network03.state.State;
import examples.chap06.network03.state.StaticElement;
import examples.chap06.network03.state.World;
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
