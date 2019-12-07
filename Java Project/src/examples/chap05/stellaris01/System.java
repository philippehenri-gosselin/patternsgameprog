/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.stellaris01;

import examples.chap05.stellaris01.immutable.ImmutableSystem;
import examples.chap05.stellaris01.mutable.MutablePlanet;

public interface System {

    public void addPlanet(MutablePlanet planet);

    public Planet getPlanet(int index);

    public Planet findPlanet(String name);

    public int getPlanetCount();

    public String getName();

    public int getX();

    public int getY();

    public void setName(String name);

    public void setX(int x);

    public void setY(int y);
    
    public ImmutableSystem toImmutable();
}
