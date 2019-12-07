/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap05.immutable01.state.immutable;

import examples.chap05.immutable01.state.Direction;
import examples.chap05.immutable01.state.Ghost;
import examples.chap05.immutable01.state.GhostStatus;

public class ImmutableGhost extends Ghost {
    
    public ImmutableGhost(Ghost ghost) {
        super(ghost.getColor());
        this.direction = ghost.getDirection();
        this.position = ghost.getPosition();
        this.speed = ghost.getSpeed();
        this.statusTime = ghost.getStatusTime();
        this.x = ghost.getX();
        this.y = ghost.getY();
        this.status = ghost.getStatus();
    }
    
    public void setDirection(Direction direction) {
        throw new IllegalAccessError();
    }

    public void setSpeed(int speed) {
        throw new IllegalAccessError();
    }

    public void setPosition(int position) {
        throw new IllegalAccessError();
    }

    public void setStatusTime(int statusTime) {
        throw new IllegalAccessError();
    }

    public void setX(int x) {
        throw new IllegalAccessError();
    }

    public void setY(int y) {
        throw new IllegalAccessError();
    }

    public void setStatus(GhostStatus status) {
        throw new IllegalAccessError();
    }
}
