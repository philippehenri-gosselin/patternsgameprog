/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.ai03.state;

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
    
    public static WallTypeId fromCode(int code) {
        switch(code) {
            case 0: return TOPLEFT;
            case 1: return TOPRIGHT;
            case 2: return BOTTOMLEFT;
            case 3: return BOTTOMRIGHT;
            case 4: return HORIZONTAL;
            case 5: return VERTICAL;
        }
        throw new IllegalArgumentException("Code "+code+" invalide");
    }
}
