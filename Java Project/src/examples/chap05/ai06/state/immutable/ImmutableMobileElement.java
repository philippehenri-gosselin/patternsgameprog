/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap05.ai06.state.immutable;

import examples.chap05.ai06.state.Direction;
import examples.chap05.ai06.state.MobileElement;
import examples.chap05.ai06.state.mutable.MutableMobileElement;

public class ImmutableMobileElement extends ImmutableElement implements MobileElement {

    public ImmutableMobileElement(MutableMobileElement element) {
        super(element);
    }

    @Override
    public Direction getDirection() {
        return ((MutableMobileElement)element).getDirection();
    }

    @Override
    public void setDirection(Direction direction) {
        throw new IllegalAccessError();
    }

    @Override
    public int getSpeed() {
        return ((MutableMobileElement)element).getSpeed();
    }

    @Override
    public void setSpeed(int speed) {
        throw new IllegalAccessError();
    }

    @Override
    public int getPosition() {
        return ((MutableMobileElement)element).getPosition();
    }

    @Override
    public void setPosition(int position) {
        throw new IllegalAccessError();
    }

    @Override
    public int getStatusTime() {
        return ((MutableMobileElement)element).getStatusTime();
    }

    @Override
    public void setStatusTime(int statusTime) {
        throw new IllegalAccessError();
    }

    @Override
    public int getX() {
        return ((MutableMobileElement)element).getX();
    }

    @Override
    public void setX(int x) {
        throw new IllegalAccessError();
    }

    @Override
    public int getY() {
        return ((MutableMobileElement)element).getY();
    }

    @Override
    public void setY(int y) {
        throw new IllegalAccessError();
    }

}
