/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap06.nanohttpserver;

public class HttpException extends Exception {
    
    private final HttpStatusCode status;

    public HttpException(HttpStatusCode status,String message) {
        super(message);
        this.status = status;
    }

    public HttpException(HttpStatusCode status) {
        this.status = status;
    }
    
    public HttpStatusCode getStatus() {
        return status;
    }

}
