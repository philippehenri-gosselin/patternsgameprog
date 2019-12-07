/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.draw03.state;

public enum Direction {

    NONE(0),
    NORTH(1),
    SOUTH(2),
    EAST(3),
    WEST(4);
    
    private final int code;
    Direction(int code) {
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }
}
