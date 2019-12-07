/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap05.immutable02.state.mutable;

import examples.chap05.immutable02.state.Characters;
import examples.chap05.immutable02.state.CharactersIterator;
import examples.chap05.immutable02.state.MobileElement;
import java.util.NoSuchElementException;

public class MutableCharactersIterator implements CharactersIterator {

    private final MutableCharacters chars;
    
    private int index;
    
    public MutableCharactersIterator(MutableCharacters chars) {
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

    @Override
    public Characters getCharacters() {
        return chars;
    }

    @Override
    public int getIndex() {
        return index;
    }

}
