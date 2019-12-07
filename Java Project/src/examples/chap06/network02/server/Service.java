/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.network02.server;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public abstract class Service {

    protected final JsonBuilderFactory jsonBuilderFactory;

    public Service(JsonBuilderFactory jsonBuilderFactory) {
        this.jsonBuilderFactory = jsonBuilderFactory;
    }
    
    public Status get(JsonObjectBuilder output, int id) throws ServiceException {
        throw new ServiceException(Status.BAD_REQUEST, "Service non implanté");
    }

    public Status post(JsonObject input, int id) throws ServiceException {
        throw new ServiceException(Status.BAD_REQUEST, "Service non implanté");
    }

    public Status put(JsonObjectBuilder output, JsonObject input) throws ServiceException {
        throw new ServiceException(Status.BAD_REQUEST, "Service non implanté");
    }

    public Status delete(int id) throws ServiceException {
        throw new ServiceException(Status.BAD_REQUEST, "Service non implanté");
    }
    
}
