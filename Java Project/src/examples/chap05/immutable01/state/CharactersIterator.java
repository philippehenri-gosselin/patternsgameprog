/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap05.immutable01.state;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CharactersIterator implements Iterator<MobileElement> {
    
    private Characters chars;
    
    private int index;
    
    public CharactersIterator(Characters chars) {
        this.chars = chars;
        this.index = -1;
    }

    @Override
    public boolean hasNext() {
        return (index+1) < chars.size();
    }

    @Override
    public MobileElement next() {
        if ((index+1) >= chars.size()) {
            throw new NoSuchElementException();
        }
        index ++;
        return chars.get(index);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
