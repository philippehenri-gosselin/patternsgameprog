/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.network02.ai;

import examples.chap06.network02.ai.map.DistanceMap;
import examples.chap06.network02.ai.mapproviders.DistanceMapProvider;
import examples.chap06.network02.rules.commands.Command;
import examples.chap06.network02.rules.commands.DirectionCommand;
import examples.chap06.network02.state.Characters;
import examples.chap06.network02.state.Direction;
import examples.chap06.network02.state.MobileElement;
import examples.chap06.network02.state.State;
import java.util.List;
import java.util.Random;

public class FleeAI extends AI {

    private String mapName;

    public FleeAI(String mapName, State state, int charIndex, CommandsLister commandsLister, Random random, DistanceMapProvider dmProvider) {
        super(state, charIndex, commandsLister, random);
        this.dmProvider = dmProvider;
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
        int maxWeight = map.getWeight(me.getX(), me.getY(), Direction.NONE);
        for (Command command : list) {
            if (!(command instanceof DirectionCommand))
                continue;
            DirectionCommand dirCommand = (DirectionCommand)command;
            Direction direction = dirCommand.getDirection();
            int weight = map.getWeight(me.getX(), me.getY(), direction);
            if (weight > maxWeight) {
                maxWeight = weight;
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
