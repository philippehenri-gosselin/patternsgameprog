/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.stellaris01.immutable;

import examples.chap05.stellaris01.Gaseous;
import examples.chap05.stellaris01.mutable.MutableGaseous;

public final class ImmutableGaseous extends ImmutablePlanet implements Gaseous {
    
    public ImmutableGaseous(MutableGaseous planet) {
        super(planet);
    }

    public ImmutablePlanet toImmutable() {
        return this;
    }
    
}
