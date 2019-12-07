/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.ai05.ai;

import examples.chap05.ai05.ai.map.DistanceMap;
import examples.chap05.ai05.ai.mapproviders.DistanceMapProvider;
import examples.chap05.ai05.rules.commands.Command;
import examples.chap05.ai05.state.Characters;
import examples.chap05.ai05.state.Direction;
import examples.chap05.ai05.state.Pacman;
import examples.chap05.ai05.state.PacmanStatus;
import examples.chap05.ai05.state.State;
import java.util.Random;

public class PacmanAI extends AI {

    private AI eatAI;
    
    private AI trackAI;

    private AI fleeAI;

    public PacmanAI(State state, CommandsLister commandsLister, Random random, DistanceMapProvider dmProvider) {
        super(state, 0, commandsLister, random);
        this.dmProvider = dmProvider;
        eatAI = new TrackAI("Gums",state,charIndex,commandsLister,random,dmProvider);
        trackAI = new TrackAI("FleeingGhosts",state,charIndex,commandsLister,random,dmProvider);
        fleeAI = new FleeAI("TrackingGhosts",state,charIndex,commandsLister,random,dmProvider);
    }

    public Command createCommand() {
        Characters chars = state.getChars();
        Pacman pacman = chars.getPacman();
        DistanceMap map = dmProvider.getMap("TrackingGhosts");
        if (map.getWeight(pacman.getX(), pacman.getY(), Direction.NONE) < 5) {
            return fleeAI.createCommand();
        }
        if (pacman.getStatus() == PacmanStatus.SUPER && pacman.getStatusTime() > 5) {
            return trackAI.createCommand();
        }
        return eatAI.createCommand();
    }
}
