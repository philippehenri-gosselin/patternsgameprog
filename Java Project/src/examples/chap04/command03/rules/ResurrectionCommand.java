/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.command03.rules;

import examples.chap04.command03.state.Characters;
import examples.chap04.command03.state.Direction;
import examples.chap04.command03.state.Ghost;
import examples.chap04.command03.state.GhostStatus;
import examples.chap04.command03.state.Space;
import examples.chap04.command03.state.SpaceTypeId;
import examples.chap04.command03.state.State;
import examples.chap04.command03.state.StaticElement;
import examples.chap04.command03.state.World;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResurrectionCommand implements Command {

    private int charIndex;
    
    public ResurrectionCommand(int charIndex) {
        this.charIndex = charIndex;
    }

    public void execute(State state) {
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
                    ghost.setStatus(GhostStatus.TRACK);
                    ghost.setStatusTime(0);
                    state.notifyCharacterChanged(charIndex);
                }
            }
        }
    }
}
