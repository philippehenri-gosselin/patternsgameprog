/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.command04.rules.commands;

import examples.chap04.command04.state.Characters;
import examples.chap04.command04.state.Direction;
import examples.chap04.command04.state.Ghost;
import examples.chap04.command04.state.GhostStatus;
import examples.chap04.command04.state.Space;
import examples.chap04.command04.state.SpaceTypeId;
import examples.chap04.command04.state.State;
import examples.chap04.command04.state.StaticElement;
import examples.chap04.command04.state.World;
import examples.chap04.command04.rules.actions.Action;
import examples.chap04.command04.rules.actions.ResurrectionAction;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

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
}
