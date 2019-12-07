/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.features02.state;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class WorldIterator implements Iterator<StaticElement> {

    private World world;

    private int x;

    private int y;

    public WorldIterator(World world) {
        this.world = world;
        this.x = -1;
        this.y = 0;
    }

    public boolean hasNext() {
        if (x + 1 < world.getWidth())
            return true;
        if (y + 1 < world.getHeight())
            return true;
        return false;
    }

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

    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public World getWorld() {
        return world;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
