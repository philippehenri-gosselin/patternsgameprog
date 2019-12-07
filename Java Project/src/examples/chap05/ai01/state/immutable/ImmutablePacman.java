/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap05.ai01.state.immutable;

import examples.chap05.ai01.state.Pacman;
import examples.chap05.ai01.state.PacmanStatus;
import examples.chap05.ai01.state.mutable.MutablePacman;

public final class ImmutablePacman extends ImmutableMobileElement implements Pacman {

    public ImmutablePacman(MutablePacman element) {
        super(element);
    }

    @Override
    public PacmanStatus getStatus() {
        return ((MutablePacman)element).getStatus();
    }

    @Override
    public void setStatus(PacmanStatus status) {
        throw new IllegalAccessError();
    }

}
