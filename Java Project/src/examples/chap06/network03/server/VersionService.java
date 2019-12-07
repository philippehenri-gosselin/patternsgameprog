/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.network03.server;

import javax.json.JsonBuilderFactory;
import javax.json.JsonObjectBuilder;

public class VersionService extends Service {

    public VersionService(JsonBuilderFactory jsonBuilderFactory) {
        super(jsonBuilderFactory);
    }

    public Status get(JsonObjectBuilder output, int id) {
        output.add("major", 1);
        output.add("minor", 0);
        return Status.OK;
    }
}
