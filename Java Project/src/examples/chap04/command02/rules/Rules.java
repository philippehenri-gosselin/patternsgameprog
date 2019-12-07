/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.command02.rules;

import examples.chap04.command02.state.State;
import java.util.TreeMap;

public class Rules {

    private State state;

    private TreeMap<Integer,Command> commands = new TreeMap();
    
    public Rules(State state) {
        this.state = state;
    }

    public void addCommand(int priority,Command command) {
        commands.put(priority,command);
    }

    public void update() {
        for (Command command : commands.values()) {
            command.execute(state);
        }
        commands.clear();
        state.incEpoch();
    }
}
