/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package pacman.state.immutable;

import pacman.state.Pacman;
import pacman.state.PacmanStatus;
import pacman.state.mutable.MutablePacman;

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
