/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.stellaris01.immutable;

import examples.chap05.stellaris01.Planet;
import examples.chap05.stellaris01.System;
import examples.chap05.stellaris01.mutable.MutablePlanet;
import examples.chap05.stellaris01.mutable.MutableSystem;

public final class ImmutableSystem implements System {
    
    private final MutableSystem system;

    public ImmutableSystem(MutableSystem system) {
        this.system = system;
    }

    public void addPlanet(MutablePlanet planet) {
        throw new IllegalAccessError();
    }

    public Planet getPlanet(int index) {
        return system.getPlanet(index).toImmutable();
    }

    public Planet findPlanet(String name) {
        return system.findPlanet(name);
    }

    public int getPlanetCount() {
        return system.getPlanetCount();
    }

    public String getName() {
        return system.getName();
    }

    public int getX() {
        return system.getX();
    }

    public int getY() {
        return system.getY();
    }

    public void setName(String name) {
        throw new IllegalAccessError();
    }

    public void setX(int x) {
        throw new IllegalAccessError();
    }

    public void setY(int y) {
        throw new IllegalAccessError();
    }

    public ImmutableSystem toImmutable() {
        return this;
    }
}
