/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.immutable01.state;

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

    static public Direction fromCode(int code) {
        switch(code) {
            case 0: return NONE;
            case 1: return NORTH;
            case 2: return SOUTH;
            case 3: return EAST;
            case 4: return WEST;            
        }
        throw new IllegalArgumentException("Code "+code+" invalide");
    }    
    
    public boolean isOppositeOf(Direction other) {
        if (this == EAST && other == WEST)
            return true;
        if (this == WEST && other == EAST)
            return true;
        if (this == SOUTH && other == NORTH)
            return true;
        if (this == NORTH && other == SOUTH)
            return true;
        return false;
    }
    
}
