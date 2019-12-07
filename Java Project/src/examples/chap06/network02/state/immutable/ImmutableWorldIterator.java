/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap06.network02.state.immutable;

import examples.chap06.network02.state.Direction;
import examples.chap06.network02.state.StaticElement;
import examples.chap06.network02.state.World;
import examples.chap06.network02.state.WorldIterator;
import java.util.NoSuchElementException;

public final class ImmutableWorldIterator implements WorldIterator {

    private final ImmutableWorld world;

    private int x;

    private int y;

    public ImmutableWorldIterator(ImmutableWorld world) {
        this.world = world;
        this.x = -1;
        this.y = 0;
    }

    @Override
    public boolean hasNext() {
        if (x + 1 < world.getWidth())
            return true;
        if (y + 1 < world.getHeight())
            return true;
        return false;
    }

    @Override
    public StaticElement next() {
        if (x + 1 < world.getWidth()) {
            x++;
        } else if (y + 1 < world.getHeight()) {
            x = 0;
            y++;
        } else {
            throw new NoSuchElementException();
        }
        return world.get(x, y, Direction.NONE);
    }

    @Override
    public void remove() {
        throw new IllegalAccessError();
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }


}
