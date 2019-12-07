/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.ai04.state;

public enum GhostStatus {

    TRACK(0), EYES(1), FLEE(2);
    
    private final int code;
    GhostStatus(int code) {
        this.code = code; 
    }
    
    public int getCode() {
        return code;
    }
    
    public static GhostStatus fromCode(int code) {
        switch(code) {
            case 0: return TRACK;
            case 1: return EYES;
            case 2: return FLEE;
        }
        throw new IllegalArgumentException("Code "+code+" invalide");
    }      
}
