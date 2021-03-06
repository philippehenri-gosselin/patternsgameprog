/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.threads02.ai.mapproviders;

import examples.chap06.threads02.ai.map.Dijkstra;
import examples.chap06.threads02.state.Space;
import examples.chap06.threads02.state.SpaceTypeId;
import examples.chap06.threads02.state.State;
import examples.chap06.threads02.state.StaticElement;
import examples.chap06.threads02.state.World;
import examples.chap06.threads02.state.WorldIterator;

public class GraveyardMapMaintainer extends DistanceMapMaintainer {

    public void stateChanged(State state) {
        super.stateChanged(state);
        World world = state.getWorld();
        Dijkstra dijkstra = new Dijkstra(world,map);
        WorldIterator iterator = world.iterator();
        while(iterator.hasNext()) {
            StaticElement se = iterator.next();
            if (!(se instanceof Space))
                continue;
            Space space = (Space)se;
            SpaceTypeId id = space.getSpaceTypeId();
            if (id == SpaceTypeId.GRAVEYARD) {
                dijkstra.addSink(iterator.getX(), iterator.getY());
            }
        }
        dijkstra.run();    
    }
}
