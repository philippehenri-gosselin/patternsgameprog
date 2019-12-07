/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.stellaris01.immutable;

import examples.chap05.stellaris01.Planet;
import examples.chap05.stellaris01.mutable.MutablePlanet;

public abstract class ImmutablePlanet implements Planet {
    
    protected final MutablePlanet planet;

    public ImmutablePlanet(MutablePlanet planet) {
        this.planet = planet;
    }

    public String getName() {
        return planet.getName();
    }

    public void setName(String name) {
        throw new IllegalAccessError();
    }
    
}
