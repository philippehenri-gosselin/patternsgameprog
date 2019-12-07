/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.network02.server;

public class ServiceException extends Exception {

    private final Status status;
    
    public ServiceException(Status status,String message) {
        super(message);
        this.status = status;
    }
    
    public Status getStatus() {
        return status;
    }
}
