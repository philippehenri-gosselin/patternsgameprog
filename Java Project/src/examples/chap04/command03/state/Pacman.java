/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.command03.state;

public class Pacman extends MobileElement {

    private PacmanStatus status = PacmanStatus.NORMAL;

    public PacmanStatus getStatus() {
        return status;
    }

    public void setStatus(PacmanStatus status) {
        this.status = status;
    }
}
