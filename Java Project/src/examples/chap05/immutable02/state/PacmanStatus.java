/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.immutable02.state;

public enum PacmanStatus {

    NORMAL(0), SUPER(1), DEAD(2);
    
    private final int code;
    PacmanStatus(int code) {
        this.code = code; 
    }
    
    public int getCode() {
        return code;
    }
    
    public static PacmanStatus fromCode(int code) {
        switch(code) {
            case 0: return NORMAL;
            case 1: return SUPER;
            case 2: return DEAD;
        }
        throw new IllegalArgumentException("Code "+code+" invalide");
    }    
}
