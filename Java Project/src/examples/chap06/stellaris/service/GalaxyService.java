/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.stellaris.service;

import examples.chap06.stellaris.ServiceException;
import examples.chap06.stellaris.Status;
import examples.chap06.stellaris.state.Universe;
import examples.chap06.stellaris.state.Galaxy;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class GalaxyService extends Service {

    private Universe universe;
    
    public GalaxyService(JsonBuilderFactory jsonBuilderFactory, Universe universe) {
        super(jsonBuilderFactory);
        this.universe = universe;
    }

    public Status get(JsonObjectBuilder output, int id) throws ServiceException {
        if (id > 0) {
            Galaxy galaxy = universe.getGalaxy(id-1);
            if (galaxy == null) {
                throw new ServiceException(Status.NOT_FOUND, "Pas de galaxie "+id);
            }
            galaxy.toJson(output);
        }
        else {
            universe.toJson(output);
        }
        return Status.OK;
    }

    public Status post(JsonObject input, int id) throws ServiceException {
        Galaxy galaxy = universe.getGalaxy(id-1);
        if (galaxy == null) {
            throw new ServiceException(Status.NOT_FOUND, "Pas de galaxie "+id);
        }
        galaxy.fromJson(input);
        return Status.NO_CONTENT;
    }

    public Status put(JsonObjectBuilder output, JsonObject input) throws ServiceException {
        int index = universe.createGalaxy(input.getString("name"));
        if (index < 0) {
            throw new ServiceException(Status.FORBIDDEN, "Plus de place");
        }
        output.add("id",index+1);
        output.add("path",universe.getGalaxy(index).getPath());
        return Status.CREATED;
    }

    public Status delete(int id) throws ServiceException {
        if (!universe.removeGalaxy(id-1)) {
            throw new ServiceException(Status.NOT_FOUND, "Pas de galaxie "+id);
        }
        return Status.NO_CONTENT;
    }
    
}
