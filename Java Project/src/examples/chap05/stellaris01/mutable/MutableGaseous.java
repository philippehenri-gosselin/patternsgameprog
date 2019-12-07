/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.stellaris01.mutable;

import examples.chap05.stellaris01.Gaseous;
import examples.chap05.stellaris01.immutable.ImmutableGaseous;
import examples.chap05.stellaris01.immutable.ImmutablePlanet;

public class MutableGaseous extends MutablePlanet implements Gaseous {

    public MutableGaseous(String name) {
        super(name);
    }

    public ImmutablePlanet toImmutable() {
        return new ImmutableGaseous(this);
    }
}
