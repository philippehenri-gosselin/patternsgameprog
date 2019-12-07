/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.network01.ai.mapproviders;

import examples.chap06.network01.ai.map.DistanceMap;
import examples.chap06.network01.state.State;
import examples.chap06.network01.state.StateObserver;
import examples.chap06.network01.state.World;

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
