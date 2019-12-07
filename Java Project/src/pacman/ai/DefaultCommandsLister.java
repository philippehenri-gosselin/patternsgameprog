/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.ai;

import pacman.rules.commands.Command;
import pacman.rules.commands.DirectionCommand;
import pacman.state.Characters;
import pacman.state.Direction;
import pacman.state.Ghost;
import pacman.state.MobileElement;
import pacman.state.Pacman;
import pacman.state.Space;
import pacman.state.SpaceTypeId;
import pacman.state.State;
import pacman.state.StaticElement;
import pacman.state.World;
import java.util.ArrayList;
import java.util.List;

public class DefaultCommandsLister implements CommandsLister {

    public List<Command> listCommands(State state, int charIndex) {
        List<Command> commands = new ArrayList();
        Characters chars = state.getChars();
        MobileElement me = chars.get(charIndex);
        if (me.getPosition() == 0) {
            int x = me.getX();
            int y = me.getY();
            World world = state.getWorld();
            for (Direction direction : Direction.allButNone) {
                StaticElement se = world.get(x, y, direction);
                if (se instanceof Space) {
                    Space space = (Space)se;
                    if (space.getSpaceTypeId() == SpaceTypeId.GRAVEYARD) {
                        if (me instanceof Pacman)
                            continue;
                    }
                    if (me instanceof Ghost && direction.isOppositeOf(me.getDirection())) {
                        continue;
                    }
                    commands.add(new DirectionCommand(charIndex,direction));
                }
            }
        }
        return commands;
    }
}
