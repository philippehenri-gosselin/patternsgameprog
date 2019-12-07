/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap05.ai06.state.mutable;

import examples.chap05.ai06.state.Direction;
import examples.chap05.ai06.state.MobileElement;

public abstract class MutableMobileElement extends MutableElement implements MobileElement 
{
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
        final MutableMobileElement other = (MutableMobileElement) obj;
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
    
    
    
    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public int getStatusTime() {
        return statusTime;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void setStatusTime(int statusTime) {
        this.statusTime = statusTime;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

}
