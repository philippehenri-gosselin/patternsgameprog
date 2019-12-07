/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.ai03.ai;

import examples.chap05.ai03.ai.map.Dijkstra;
import examples.chap05.ai03.ai.map.DistanceMap;
import examples.chap05.ai03.rules.commands.Command;
import examples.chap05.ai03.rules.commands.DirectionCommand;
import examples.chap05.ai03.state.Characters;
import examples.chap05.ai03.state.Direction;
import examples.chap05.ai03.state.MobileElement;
import examples.chap05.ai03.state.State;
import examples.chap05.ai03.state.World;
import java.util.List;
import java.util.Random;

public class TrackAI extends AI {

    private int trackIndex;

    private int trackX;

    private int trackY;

    private DistanceMap map;

    public TrackAI(int trackIndex, State state, int charIndex, CommandsLister commandsLister, Random random) {
        super(state, charIndex, commandsLister, random);
        this.trackIndex = trackIndex;
    }
    
    public DistanceMap getMap() {
        return map;
    }

    private void updateMap() {
        Characters chars = state.getChars();
        MobileElement me = chars.get(trackIndex);
        if (trackX == me.getX() && trackY == me.getY()) {
            return;
        }
        trackX = me.getX();
        trackY = me.getY();
        World world = state.getWorld();
        if (map == null) {
            map = new DistanceMap(world.getWidth(),world.getHeight());
        }
        Dijkstra dijkstra = new Dijkstra(world,map);
        dijkstra.addSink(me.getX(), me.getY());
        dijkstra.run();
    }
    
    public Command createCommand() {
        List<Command> list = commandsLister.listCommands(state, charIndex);
        if (list.isEmpty())
            return null;
        updateMap();
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
