/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.ai.mapproviders;

import pacman.ai.map.Dijkstra;
import pacman.state.Direction;
import pacman.state.Space;
import pacman.state.SpaceTypeId;
import pacman.state.State;
import pacman.state.StaticElement;
import pacman.state.World;
import pacman.state.WorldIterator;

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
