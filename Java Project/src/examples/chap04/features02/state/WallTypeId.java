/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.features02.state;

public enum WallTypeId {

    TOPLEFT(0), 
    TOPRIGHT(1), 
    BOTTOMLEFT(2), 
    BOTTOMRIGHT(3), 
    HORIZONTAL(4), 
    VERTICAL(5);
    
    private final int code;
    WallTypeId(int code) {
        this.code = code; 
    }
    
    public int getCode() {
        return code;
    }
}
