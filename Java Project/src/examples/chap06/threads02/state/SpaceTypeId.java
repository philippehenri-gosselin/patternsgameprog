/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.threads02.state;

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
    
    static public SpaceTypeId fromCode(int code) {
        switch(code) {
            case 0: return UNKNOWN;
            case 1: return EMPTY;
            case 2: return GUM;
            case 3: return SUPERGUM;
            case 4: return GRAVEYARD;
            case 5: return START;
        }
        throw new IllegalArgumentException("Code "+code+" invalide");
    }
        
}
