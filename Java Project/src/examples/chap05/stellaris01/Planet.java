/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.stellaris01;

import examples.chap05.stellaris01.immutable.ImmutablePlanet;

public interface Planet {

    public String getName();

    public void setName(String name);
    
    public ImmutablePlanet toImmutable();
    
}
