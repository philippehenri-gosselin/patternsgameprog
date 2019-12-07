/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.immutable01.state;

public class Pacman extends MobileElement {

    protected PacmanStatus status = PacmanStatus.NORMAL;

    public PacmanStatus getStatus() {
        return status;
    }

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
        final Pacman other = (Pacman) obj;
        if (this.status != other.status) {
            return false;
        }
        return true;
    }
    
    
}
