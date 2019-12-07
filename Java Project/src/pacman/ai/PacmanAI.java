/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.ai;

import pacman.ai.map.DistanceMap;
import pacman.ai.mapproviders.DistanceMapProvider;
import pacman.rules.commands.Command;
import pacman.state.Characters;
import pacman.state.Direction;
import pacman.state.Pacman;
import pacman.state.PacmanStatus;
import pacman.state.State;
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
