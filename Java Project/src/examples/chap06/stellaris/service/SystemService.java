/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.stellaris.service;

import examples.chap06.stellaris.ServiceException;
import examples.chap06.stellaris.Status;
import examples.chap06.stellaris.state.Galaxy;
import examples.chap06.stellaris.state.System;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class SystemService extends Service {

    private final Galaxy galaxy;

    public SystemService(JsonBuilderFactory jsonBuilderFactory,Galaxy galaxy) {
        super(jsonBuilderFactory);
        this.galaxy = galaxy;
    }

    public Status get(JsonObjectBuilder output, int id) throws ServiceException {
        if (id > 0) {
            System system = galaxy.getSystem(id-1);
            if (system == null) {
                throw new ServiceException(Status.NOT_FOUND, "Pas de système "+id);
            }
            system.toJson(output);
        }
        else {
            galaxy.buildJsonSystems(output);       
        }
        return Status.OK;
    }

    public Status post(JsonObject input, int id) throws ServiceException {
        System system = galaxy.getSystem(id-1);
        if (system == null) {
            throw new ServiceException(Status.NOT_FOUND, "Pas de système "+id);
        }
        system.fromJson(input);
        return Status.NO_CONTENT;
    }

    public Status put(JsonObjectBuilder output, JsonObject input) throws ServiceException {
        int index = galaxy.createSystem(input.getString("name"),input.getInt("x"),input.getInt("y"));
        if (index < 0) {
            throw new ServiceException(Status.FORBIDDEN, "Plus de place");
        }
        output.add("id",index+1);
        output.add("path",galaxy.getSystem(index).getPath());
        return Status.CREATED;
    }

    public Status delete(int id) throws ServiceException {
        if (!galaxy.removeSystem(id-1)) {
            throw new ServiceException(Status.NOT_FOUND, "Pas de système "+id);
        }
        return Status.NO_CONTENT;
    }
}
