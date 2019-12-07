/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.stellaris;

import examples.chap02.stellaris.Planet;

public interface PlanetCreator {

    public Planet create(String name);
}
