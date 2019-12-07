/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap05.ai03.state.immutable;

import examples.chap05.ai03.state.Characters;
import examples.chap05.ai03.state.CharactersIterator;
import examples.chap05.ai03.state.Ghost;
import examples.chap05.ai03.state.MobileElement;
import examples.chap05.ai03.state.Pacman;
import examples.chap05.ai03.state.mutable.MutableCharacters;
import examples.chap05.ai03.state.mutable.MutableGhost;
import examples.chap05.ai03.state.mutable.MutablePacman;

public final class ImmutableCharacters implements Characters {
    
    private final MutableCharacters chars;
    
    public ImmutableCharacters(MutableCharacters chars) {
        this.chars = chars;
    }

    @Override
    public int size() {
        return chars.size();
    }

    @Override
    public boolean isEmpty() {
        return chars.isEmpty();
    }

    @Override
    public MobileElement get(int index) {
        MobileElement me = chars.get(index);
        return (ImmutableMobileElement)me.toImmutable();
    }

    @Override
    public Pacman getPacman() {
        Pacman pacman = chars.getPacman();
        return new ImmutablePacman((MutablePacman)pacman);
    }

    @Override
    public Ghost getGhost(int index) {
        Ghost pacman = chars.getGhost(index);
        return new ImmutableGhost((MutableGhost)pacman);
    }

    @Override
    public void init(int[][] level) {
        throw new IllegalAccessError();
    }

    @Override
    public void add(MobileElement me) {
        throw new IllegalAccessError();
    }

    @Override
    public void set(int index, MobileElement me) {
        throw new IllegalAccessError();
    }

    @Override
    public CharactersIterator iterator() {
        return new ImmutableCharactersIterator(this);
    }

    @Override
    public Characters clone() {
        return chars.clone();
    }

}
