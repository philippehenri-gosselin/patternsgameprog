/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.ai;

import pacman.rules.commands.Command;
import pacman.rules.commands.DirectionCommand;
import pacman.state.Characters;
import pacman.state.Direction;
import pacman.state.MobileElement;
import pacman.state.State;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExplorationAI extends AI {

    public ExplorationAI(State state, int charIndex, CommandsLister commandsLister, Random random) {
        super(state, charIndex, commandsLister, random);
    }
    
    private boolean findCharacter(Direction direction) {
        Characters chars = state.getChars();        
        MobileElement me = chars.get(charIndex);
        int x = me.getX();
        int y = me.getY();
        for (int index=0;index<chars.size();index++) {
            if (index == charIndex)
                continue;
            me = chars.get(index);
            int cx = me.getX();
            int cy = me.getY();
            boolean found = false;
            switch(direction) {
                case EAST:
                    found = cx >= x+1 && cx <= x+3 && cy >= y-2 && cy <= y+2;
                    break;
                case WEST:
                    found = cx >= x-3 && cx <= x-1 && cy >= y-2 && cy <= y+2;
                    break;
                case SOUTH:
                    found = cx >= x-2 && cx <= x+2 && cy >= y+1 && cy <= y+3;
                    break;
                case NORTH:
                    found = cx >= x-2 && cx <= x+2 && cy >= y-3 && cy <= y-1;
                    break;
            }
            if (found)
                return true;
        }
        return false;
    }

    public Command createCommand() {
        List<Command> list = commandsLister.listCommands(state, charIndex);
        if (list.isEmpty())
            return null;
        Characters chars = state.getChars();        
        MobileElement me = chars.get(charIndex);
        Command oppositeCommand = null;
        List<Command> commands = new ArrayList();
        for (Command command : list) {
            if (!(command instanceof DirectionCommand))
                continue;
            DirectionCommand dirCommand = (DirectionCommand)command;
            Direction direction = dirCommand.getDirection();
            if (findCharacter(dirCommand.getDirection()))
                continue;
            if (direction.isOppositeOf(me.getDirection())) {
                oppositeCommand = command;
                continue;
            }
            commands.add(command);
        }
        if (!commands.isEmpty()) {
            int index = random.nextInt(commands.size());
            return commands.get(index);
        }
        if (oppositeCommand != null) {
            return oppositeCommand;
        }
        return null;
    }
}
