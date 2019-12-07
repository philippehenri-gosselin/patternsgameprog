/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.immutable01.state;

public abstract class MobileElement extends Element {

    protected Direction direction = Direction.NONE;

    protected int speed = 2;

    protected int position;

    protected int statusTime;

    protected int x;

    protected int y;

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
        final MobileElement other = (MobileElement) obj;
        if (this.speed != other.speed) {
            return false;
        }
        if (this.position != other.position) {
            return false;
        }
        if (this.statusTime != other.statusTime) {
            return false;
        }
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        if (this.direction != other.direction) {
            return false;
        }
        return true;
    }
    
    
    
    public Direction getDirection() {
        return direction;
    }

    public int getSpeed() {
        return speed;
    }

    public int getPosition() {
        return position;
    }

    public int getStatusTime() {
        return statusTime;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setStatusTime(int statusTime) {
        this.statusTime = statusTime;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
