/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.stellaris01.immutable;

import examples.chap05.stellaris01.Connection;
import examples.chap05.stellaris01.Galaxy;
import examples.chap05.stellaris01.System;
import examples.chap05.stellaris01.mutable.MutableConnection;
import examples.chap05.stellaris01.mutable.MutableGalaxy;
import examples.chap05.stellaris01.mutable.MutableSystem;

public final class ImmutableGalaxy implements Galaxy {
    
    private final MutableGalaxy galaxy;

    public ImmutableGalaxy(MutableGalaxy galaxy) {
        this.galaxy = galaxy;
    }

    public MutableSystem createSystem(String name, int x, int y) {
        throw new IllegalAccessError();
    }

    public MutableConnection connectSystems(String name1, String name2, int parsecs) {
        throw new IllegalAccessError();
    }

    public int findSystem(String name) {
        return galaxy.findSystem(name);
    }
    
    public int getSystemCount() {
        return galaxy.getSystemCount();
    }

    public System getSystem(int index) {
        return galaxy.getSystem(index).toImmutable();
    }
    
    public int findConnection(int index1, int index2) {
        return galaxy.findConnection(index1, index2);
    }
    
    public int getConnectionCount() {
        return galaxy.getConnectionCount();
    }            
    
    public Connection getConnection(int index) {
        return galaxy.getConnection(index).toImmutable();
    }
    
}
