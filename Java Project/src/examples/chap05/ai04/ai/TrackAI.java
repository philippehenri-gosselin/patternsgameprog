/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.ai04.ai;

import examples.chap05.ai04.ai.map.DistanceMap;
import examples.chap05.ai04.rules.commands.Command;
import examples.chap05.ai04.rules.commands.DirectionCommand;
import examples.chap05.ai04.state.Characters;
import examples.chap05.ai04.state.Direction;
import examples.chap05.ai04.state.MobileElement;
import examples.chap05.ai04.state.State;
import java.util.List;
import java.util.Random;

public class TrackAI extends AI {

    private String mapName;

    public TrackAI(String mapName, State state, int charIndex, CommandsLister commandsLister, Random random) {
        super(state, charIndex, commandsLister, random);
        this.mapName = mapName;
    }

    public Command createCommand() {
        List<Command> list = commandsLister.listCommands(state, charIndex);
        if (list.isEmpty())
            return null;
        DistanceMap map = dmProvider.getMap(mapName);
        Command bestCommand = null;
        Characters chars = state.getChars();
        MobileElement me = chars.get(charIndex);
        int minWeight = map.getWeight(me.getX(), me.getY(), Direction.NONE);
        for (Command command : list) {
            if (!(command instanceof DirectionCommand))
                continue;
            DirectionCommand dirCommand = (DirectionCommand)command;
            Direction direction = dirCommand.getDirection();
            int weight = map.getWeight(me.getX(), me.getY(), direction);
            if (weight < minWeight) {
                minWeight = weight;
                bestCommand = command;
            }
        }    
        if (bestCommand == null) {
            int index = random.nextInt(list.size());
            bestCommand = list.get(index);
        }
        return bestCommand;
    }
}
