/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.ai.mapproviders;

import pacman.ai.map.DistanceMap;
import pacman.state.State;
import pacman.state.StateObserver;
import pacman.state.World;

public abstract class DistanceMapMaintainer implements StateObserver {

    protected DistanceMap map;

    public DistanceMap getMap() {
        return map;
    }

    public void stateChanged(State state) {
       World world = state.getWorld();
       map = new DistanceMap(world.getWidth(),world.getHeight());
    }

    public void worldElementChanged(State state, int x, int y) {
    }

    public void characterChanged(State state, int charIndex) {
    }
}
