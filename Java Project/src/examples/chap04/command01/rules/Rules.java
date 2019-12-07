/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.command01.rules;

import examples.chap04.command01.state.State;
import java.util.ArrayList;
import java.util.List;

public class Rules {

    private State state;

    private List<Command> commands = new ArrayList();
    
    public Rules(State state) {
        this.state = state;
    }

    public void addCommand(Command command) {
        commands.add(command);
    }

    public void update() {
        for (Command command : commands) {
            command.execute(state);
        }
        commands.clear();
        state.incEpoch();
    }
}
