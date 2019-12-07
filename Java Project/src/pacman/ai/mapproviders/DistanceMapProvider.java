/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.ai.mapproviders;

import pacman.ai.map.DistanceMap;
import pacman.state.State;
import pacman.state.StateObserver;
import java.util.HashMap;
import java.util.Map;

public class DistanceMapProvider implements StateObserver {

    private Map<String, DistanceMapMaintainer> maintainers = new HashMap();

    public void registerMaintainer(String name, DistanceMapMaintainer m) {
        maintainers.put(name, m);
    }

    public void unregisterMaintainer(String name) {
        maintainers.remove(name);
    }

    public DistanceMap getMap(String name) {
        DistanceMapMaintainer maintainer = maintainers.get(name);
        return maintainer.getMap();
    }

    public void stateChanged(State state) {
        for (DistanceMapMaintainer m : maintainers.values()) {
            m.stateChanged(state);
        }
    }

    public void worldElementChanged(State state, int x, int y) {
        for (DistanceMapMaintainer m : maintainers.values()) {
            m.worldElementChanged(state,x,y);
        }
    }

    public void characterChanged(State state, int charIndex) {
        for (DistanceMapMaintainer m : maintainers.values()) {
            m.characterChanged(state,charIndex);
        }
    }
}
