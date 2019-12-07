/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap05.immutable01.state.immutable;

import examples.chap05.immutable01.state.Direction;
import examples.chap05.immutable01.state.Pacman;
import examples.chap05.immutable01.state.PacmanStatus;

public class ImmutablePacman extends Pacman {
    
    public ImmutablePacman(Pacman pacman) {
        this.direction = pacman.getDirection();
        this.position = pacman.getPosition();
        this.speed = pacman.getSpeed();
        this.statusTime = pacman.getStatusTime();
        this.x = pacman.getX();
        this.y = pacman.getY();
        this.status = pacman.getStatus();
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

    public void setStatus(PacmanStatus status) {
        throw new IllegalAccessError();
    }
}
