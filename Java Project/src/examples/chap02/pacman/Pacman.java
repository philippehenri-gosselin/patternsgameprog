/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap02.pacman;

public class Pacman extends MobileElement {

    private PacmanStatus status;

    public PacmanStatus getStatus() {
        return status;
    }

    public void setStatus(PacmanStatus status) {
        this.status = status;
    }
}
