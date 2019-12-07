/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.ai04.state;

public interface MobileElement extends Element {

public Direction getDirection();

public void setDirection(Direction direction);

public int getSpeed();

public void setSpeed(int speed);

public int getPosition();

public void setPosition(int position);

public int getStatusTime();

public void setStatusTime(int statusTime);

public int getX();

public void setX(int x);

public int getY();

public void setY(int y);

public boolean equals(Object obj);
}
