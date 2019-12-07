/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.state;

public interface Ghost extends MobileElement {

    public int getColor();

    public void setColor(int color);

    public GhostStatus getStatus();

    public void setStatus(GhostStatus status);

    public boolean equals(Object obj);
}
