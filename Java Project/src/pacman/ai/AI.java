/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.ai;

import pacman.ai.mapproviders.DistanceMapProvider;
import pacman.rules.commands.Command;
import pacman.state.State;
import java.util.Random;

public abstract class AI {

    protected State state;

    protected int charIndex;

    protected CommandsLister commandsLister;
    
    protected Random random;
    
    protected DistanceMapProvider dmProvider;
    
    public AI(State state, int charIndex, CommandsLister commandsLister, Random random) {
        this.state = state;
        this.charIndex = charIndex;
        this.commandsLister = commandsLister;
        this.random = random;
    }
    
    public abstract Command createCommand();

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getCharIndex() {
        return charIndex;
    }

    public void setCharIndex(int charIndex) {
        this.charIndex = charIndex;
    }

    public CommandsLister getCommandsLister() {
        return commandsLister;
    }

    public void setCommandsLister(CommandsLister commandsLister) {
        this.commandsLister = commandsLister;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public DistanceMapProvider getDmProvider() {
        return dmProvider;
    }

    public void setDmProvider(DistanceMapProvider dmProvider) {
        this.dmProvider = dmProvider;
    }
    
}
