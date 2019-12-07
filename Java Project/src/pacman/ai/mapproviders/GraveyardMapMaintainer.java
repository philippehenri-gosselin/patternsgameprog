/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.ai.mapproviders;

import pacman.ai.map.Dijkstra;
import pacman.state.Space;
import pacman.state.SpaceTypeId;
import pacman.state.State;
import pacman.state.StaticElement;
import pacman.state.World;
import pacman.state.WorldIterator;

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
