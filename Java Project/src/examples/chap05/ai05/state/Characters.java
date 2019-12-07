/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.ai05.state;

import java.util.Iterator;

public interface Characters extends Iterable<MobileElement> {

    public int size();

    public boolean isEmpty();

    public MobileElement get(int index);

    public Pacman getPacman();

    public Ghost getGhost(int index);

    public void init(int[][] level);

    public void add(MobileElement me);

    public void set(int index, MobileElement me);

    public CharactersIterator iterator();

    public boolean equals(Object obj);

    public Characters clone();
}
