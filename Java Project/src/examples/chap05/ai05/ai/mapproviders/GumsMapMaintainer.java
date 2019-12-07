/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.ai05.ai.mapproviders;

import examples.chap05.ai05.ai.map.Dijkstra;
import examples.chap05.ai05.state.Direction;
import examples.chap05.ai05.state.Space;
import examples.chap05.ai05.state.SpaceTypeId;
import examples.chap05.ai05.state.State;
import examples.chap05.ai05.state.StaticElement;
import examples.chap05.ai05.state.World;
import examples.chap05.ai05.state.WorldIterator;

public class GumsMapMaintainer extends DistanceMapMaintainer {

    private void update(State state) 
    {
        World world = state.getWorld();
        Dijkstra dijkstra = new Dijkstra(world,map);
        WorldIterator iterator = world.iterator();
        while(iterator.hasNext()) {
            StaticElement se = iterator.next();
            if (!(se instanceof Space))
                continue;
            Space space = (Space)se;
            SpaceTypeId id = space.getSpaceTypeId();
            if (id == SpaceTypeId.GUM || id == SpaceTypeId.SUPERGUM) {
                dijkstra.addSink(iterator.getX(), iterator.getY());
            }
        }
        dijkstra.run();  
    }
    
    public void stateChanged(State state) {
        super.stateChanged(state);
        update(state);
    }

    public void worldElementChanged(State state, int x, int y) {
        update(state);
    }
}
