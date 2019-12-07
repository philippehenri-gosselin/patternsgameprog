/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.server;

import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class GameService extends Service {

    private Game game;

    public GameService(JsonBuilderFactory jsonBuilderFactory, Game game) {
        super(jsonBuilderFactory);
        this.game = game;
    }

    public Status get(JsonObjectBuilder output, int id) {
        if (game.isRunning())
            return Status.OK;
        return Status.NOT_FOUND;
    }

    public Status put(JsonObjectBuilder output, JsonObject input) {
        if (game.isRunning())
            return Status.FORBIDDEN;
        game.start();
        return Status.CREATED;
    }
}
