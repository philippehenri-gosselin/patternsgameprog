/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.command04.state;

public class Ghost extends MobileElement {

    private GhostStatus status = GhostStatus.TRACK;

    private int color;

    public Ghost(int color) {
        this.color = color;
    }

    public GhostStatus getStatus() {
        return status;
    }

    public int getColor() {
        return color;
    }

    public void setStatus(GhostStatus status) {
        this.status = status;
    }

    public void setColor(int color) {
        this.color = color;
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
        final Ghost other = (Ghost) obj;
        if (this.color != other.color) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        return true;
    }
    
}
