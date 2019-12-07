/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.threads02.state;

import java.util.Iterator;

public interface CharactersIterator extends Iterator<MobileElement> {

    public Characters getCharacters();

    public int getIndex();

    public boolean hasNext();

    public MobileElement next();

    public void remove();
}
