/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.ai03.ai;

import examples.chap05.ai03.rules.commands.Command;
import examples.chap05.ai03.rules.commands.DirectionCommand;
import examples.chap05.ai03.state.Characters;
import examples.chap05.ai03.state.Direction;
import examples.chap05.ai03.state.Ghost;
import examples.chap05.ai03.state.MobileElement;
import examples.chap05.ai03.state.Pacman;
import examples.chap05.ai03.state.Space;
import examples.chap05.ai03.state.SpaceTypeId;
import examples.chap05.ai03.state.State;
import examples.chap05.ai03.state.StaticElement;
import examples.chap05.ai03.state.World;
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
