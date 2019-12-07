/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.stellaris.router;

import examples.chap06.stellaris.ServiceException;
import examples.chap06.stellaris.ServiceQuery;
import examples.chap06.stellaris.service.Service;
import javax.json.JsonBuilderFactory;

public abstract class ServiceRouter {

    protected final JsonBuilderFactory jsonBuilderFactory;

    public ServiceRouter(JsonBuilderFactory jsonBuilderFactory) {
        this.jsonBuilderFactory = jsonBuilderFactory;
    }

    public Service matchService(ServiceQuery query) throws ServiceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
