/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.stellaris;

public enum Status {

    OK(200),
    CREATED(201),
    NO_CONTENT(204),
    BAD_REQUEST(400),
    FORBIDDEN(403),
    NOT_FOUND(404),
    BAD_METHOD(405);
    
    private final int code;
    Status(int code) {
        this.code = code; 
    }
    
    public int getCode() {
        return code;
    }    
}
