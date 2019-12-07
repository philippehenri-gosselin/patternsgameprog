/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.draw03.state;

public enum SpaceTypeId {

    UNKNOWN(0),
    EMPTY(1),
    GUM(2),
    SUPERGUM(3),
    GRAVEYARD(4),
    START(5);
    
    private final int code;
    SpaceTypeId(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }    
}
