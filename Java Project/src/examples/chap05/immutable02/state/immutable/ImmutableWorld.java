/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap05.immutable02.state.immutable;

import examples.chap05.immutable02.state.Direction;
import examples.chap05.immutable02.state.ElementFactory;
import examples.chap05.immutable02.state.StaticElement;
import examples.chap05.immutable02.state.World;
import examples.chap05.immutable02.state.WorldIterator;
import examples.chap05.immutable02.state.mutable.MutableWorld;

public final class ImmutableWorld implements World {
    
    private final MutableWorld world;
    
    public ImmutableWorld(MutableWorld world) {
        this.world = world;
    }

    @Override
    public int getWidth() {
        return world.getWidth();
    }

    @Override
    public int getHeight() {
        return world.getHeight();
    }

    @Override
    public StaticElement get(int x, int y, Direction d) {
        StaticElement se = world.get(x, y, d);
        return (ImmutableStaticElement)se.toImmutable();
    }

    @Override
    public void set(int x, int y, StaticElement e) {
        throw new IllegalAccessError();
    }

    @Override
    public ElementFactory getFactory() {
        return world.getFactory();
    }

    @Override
    public void setFactory(ElementFactory factory) {
        throw new IllegalAccessError();
    }
    
    @Override
    public void init(int[][] level) {
        throw new IllegalAccessError();
    }

    @Override
    public WorldIterator iterator() {
        return new ImmutableWorldIterator(this);
    }

    @Override
    public World clone() {
        return world.clone();
    }


}
