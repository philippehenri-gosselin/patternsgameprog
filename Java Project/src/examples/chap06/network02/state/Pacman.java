/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.network02.state;

public interface Pacman extends MobileElement {

public PacmanStatus getStatus();

public void setStatus(PacmanStatus status);

public boolean equals(Object obj);
}
