/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.threads02.ai;

import examples.chap06.threads02.ai.mapproviders.DistanceMapProvider;
import examples.chap06.threads02.rules.commands.Command;
import examples.chap06.threads02.state.Characters;
import examples.chap06.threads02.state.Ghost;
import examples.chap06.threads02.state.GhostStatus;
import examples.chap06.threads02.state.State;
import java.util.Random;

public class GhostAI extends AI {

    private AI exploreAI;

    private AI fleeAI;
    
    private AI resAI;

    public GhostAI(State state, int charIndex, CommandsLister commandsLister, Random random, DistanceMapProvider dmProvider) {
        super(state, charIndex, commandsLister, random);
        this.dmProvider = dmProvider;
        exploreAI = new RandomAI(state,charIndex,commandsLister,random);
        fleeAI = new FleeAI("Pacman",state,charIndex,commandsLister,random,dmProvider);
        resAI = new TrackAI("Graveyard",state,charIndex,commandsLister,random,dmProvider);
    }

    public Command createCommand() {
        Characters chars = state.getChars();
        Ghost ghost = chars.getGhost(charIndex);
        if (ghost.getStatus() == GhostStatus.FLEE) {
            return fleeAI.createCommand();
        }
        else if (ghost.getStatus() == GhostStatus.EYES) {
            return resAI.createCommand();
        }
        return exploreAI.createCommand();
    }
}
