/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.stellaris;

import examples.chap02.stellaris.Planet;
import java.util.HashMap;
import java.util.Map;

public class PlanetFactory {

    private Map<String, PlanetCreator> creators = new HashMap();

    public void registerCreator(String type, PlanetCreator creator) {
        creators.put(type, creator);
    }

    public void unregisterCreator(String type) {
        creators.remove(type);
    }

    public Planet create(String type,String name) {
        return creators.get(type).create(name);
    }
}
