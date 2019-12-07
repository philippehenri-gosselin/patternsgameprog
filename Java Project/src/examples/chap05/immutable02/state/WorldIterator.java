/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.immutable02.state;

import java.util.Iterator;

public interface WorldIterator extends Iterator<StaticElement> {

    public World getWorld();

    public int getX();

    public int getY();

    public boolean hasNext();

    public StaticElement next();

    public void remove();
}
