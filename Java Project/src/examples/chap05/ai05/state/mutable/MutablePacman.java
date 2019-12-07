/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap05.ai05.state.mutable;

import examples.chap05.ai05.state.Element;
import examples.chap05.ai05.state.Pacman;
import examples.chap05.ai05.state.PacmanStatus;
import examples.chap05.ai05.state.immutable.ImmutablePacman;

public class MutablePacman extends MutableMobileElement implements Pacman {

    protected PacmanStatus status = PacmanStatus.NORMAL;

    @Override
    public PacmanStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(PacmanStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MutablePacman other = (MutablePacman) obj;
        if (this.status != other.status) {
            return false;
        }
        return true;
    }

    @Override
    public Element toImmutable() {
        return new ImmutablePacman(this);
    }
}
