/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.rules.commands;

import pacman.rules.actions.Action;
import pacman.state.State;
import java.util.Queue;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public interface Command {

    public void execute(Queue<Action> actions,State state);

    public void toJson(JsonObjectBuilder builder);
    
    public static Command fromJson(JsonObject json) {
        String type = json.getString("type");
        switch(type) {
            case "Collisions": return new CollisionsCommand(json);
            case "Direction": return new DirectionCommand(json);
            case "Gums": return new GumsCommand(json);
            case "Init": return new InitCommand(json);
            case "LoadLevel": return new LoadLevelCommand(json);
            case "Move": return new MoveCommand(json);
            case "Resurrection": return new ResurrectionCommand(json);
            case "UpdateStatus": return new UpdateStatusCommand(json);
        }
        throw new ClassCastException("Type "+type+" invalid or unsupported");
    }
    
}
