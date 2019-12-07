/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap05.immutable01.state.immutable;

import examples.chap05.immutable01.state.Characters;
import examples.chap05.immutable01.state.Ghost;
import examples.chap05.immutable01.state.MobileElement;
import examples.chap05.immutable01.state.Pacman;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ImmutableCharacters extends Characters {

    public ImmutableCharacters(Characters characters) {
        this.chars = characters.getChars();
    }

    public void init(int[][] level) {
        throw new IllegalAccessError();
    }
    
    public void add(MobileElement me) {
        throw new IllegalAccessError();
    }
    
    public void set(int index, MobileElement me) {
        throw new IllegalAccessError();
    }
    
    public MobileElement get(int index) {
        MobileElement me = chars.get(index);
        if (me instanceof Pacman) {
            return new ImmutablePacman((Pacman)me);
        }
        if (me instanceof Ghost) {
            return new ImmutableGhost((Ghost)me);
        }
        throw new RuntimeException("Invalid element type");
    }
    
    public Pacman getPacman() {
        if (chars.isEmpty())
            return null;
        MobileElement me = chars.get(0);
        if (!(me instanceof Pacman)) 
            return null;
        return new ImmutablePacman((Pacman)me);
    }
    
    public Ghost getGhost(int index) {
        if (index >= chars.size())
            return null;
        MobileElement me = chars.get(index);
        if (!(me instanceof Ghost)) 
            return null;
        return new ImmutableGhost((Ghost)me);
    }

    public List<MobileElement> getChars() {
        return Collections.unmodifiableList(chars);
    }

    public int size() {
        return chars.size();
    }

    public boolean isEmpty() {
        return chars.isEmpty();
    }
    
    @Override
    public Iterator<MobileElement> iterator() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    private Object writeReplace() {
        throw new IllegalAccessError();
    }
    
    
}
