/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.server;

import pacman.rules.commands.Command;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class CommandService extends Service {

    private Game game;

    public CommandService(JsonBuilderFactory jsonBuilderFactory,Game game) {
        super(jsonBuilderFactory);
        this.game = game;
    }

    public Status get(JsonObjectBuilder output, int id) {
        if (!game.isRunning())
            return Status.FORBIDDEN;
        if (id > 0) {
            output.add("commands", game.getCommands(id-1));
            return Status.OK;
        }
        else {
            output.add("commands", game.getCommands(0));
            return Status.OK;
        }
    }

    public Status put(JsonObjectBuilder output, JsonObject input) {
        if (!game.isRunning())
            return Status.FORBIDDEN;
        int priority = input.getInt("priority");
        Command command = Command.fromJson(input.getJsonObject("command"));
        game.addCommand(priority, command);
        return Status.CREATED;
    }
}
