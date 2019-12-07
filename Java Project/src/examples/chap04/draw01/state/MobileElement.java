/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.draw01.state;

public abstract class MobileElement extends Element {

    protected Direction direction;

    protected int speed;

    protected int position;

    protected int statusTime;

    protected int x;

    protected int y;

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
