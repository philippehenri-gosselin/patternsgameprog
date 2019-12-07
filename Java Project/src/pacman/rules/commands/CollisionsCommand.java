/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.rules.commands;

import pacman.state.Characters;
import pacman.state.Ghost;
import pacman.state.GhostStatus;
import pacman.state.Pacman;
import pacman.state.PacmanStatus;
import pacman.state.State;
import pacman.rules.actions.Action;
import pacman.rules.actions.KillGhostAction;
import pacman.rules.actions.KillPacmanAction;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class CollisionsCommand implements Command {
    
    public CollisionsCommand() {
        
    }

    public void execute(Queue<Action> actions,State state) {
        Characters chars = state.getChars();
        Pacman pacman = chars.getPacman();
        if (pacman == null) {
            Logger.getLogger("rules").log(Level.SEVERE,"No Pacman");
            return;
        }
        if (pacman.getStatus() == PacmanStatus.DEAD)
            return;
        int px = pacman.getX();
        int py = pacman.getY();
        for (int index=1;index<chars.size();index++) {
            Ghost ghost = chars.getGhost(index);
            if (ghost == null) {
                Logger.getLogger("rules").log(Level.SEVERE,"No ghost");
                continue;
            }
            int gx = ghost.getX();
            int gy = ghost.getY();
            if (gx != px || gy != py)
                continue;
            if (ghost.getStatus() == GhostStatus.EYES)
                continue;
            if (ghost.getStatus() == GhostStatus.FLEE) {
                Action action = new KillGhostAction(index);
                action.apply(state);
                actions.add(action);
            }
            else if (ghost.getStatus() == GhostStatus.TRACK) {
                Action action = new KillPacmanAction();
                action.apply(state);
                actions.add(action);
            }            
        }
    }
    
    public void toJson(JsonObjectBuilder builder) {
        builder
            .add("type", "Collisions");
    }
    
    public CollisionsCommand(JsonObject json) {
    }
}
