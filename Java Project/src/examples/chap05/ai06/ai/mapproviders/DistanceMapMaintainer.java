/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.ai06.ai.mapproviders;

import examples.chap05.ai06.ai.map.DistanceMap;
import examples.chap05.ai06.state.State;
import examples.chap05.ai06.state.StateObserver;
import examples.chap05.ai06.state.World;

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
