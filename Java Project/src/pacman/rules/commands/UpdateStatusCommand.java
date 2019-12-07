/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.rules.commands;

import pacman.state.Characters;
import pacman.state.Ghost;
import pacman.state.GhostStatus;
import pacman.state.MobileElement;
import pacman.state.Pacman;
import pacman.state.PacmanStatus;
import pacman.state.State;
import pacman.rules.actions.Action;
import pacman.rules.actions.GhostStatusAction;
import pacman.rules.actions.PacmanStatusAction;
import pacman.rules.actions.UpdateStatusAction;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class UpdateStatusCommand implements Command {

    private int charIndex;

    public UpdateStatusCommand(int charIndex) {
        this.charIndex = charIndex;
    }

    public void execute(Queue<Action> actions,State state) {
        Characters chars = state.getChars();
        MobileElement me = chars.get(charIndex);
        if (me == null) {
            Logger.getLogger("rules").log(Level.SEVERE,"Pas de personnage");
            return;
        }
        int time = me.getStatusTime();
        if (time <= 0) {
            return;
        }
        Action action = new UpdateStatusAction(charIndex);
        action.apply(state);
        actions.add(action);
        if (me.getStatusTime() == 0) {
            if (me instanceof Pacman) {
                Pacman pacman = (Pacman)me;
                if (pacman.getStatus() == PacmanStatus.SUPER) {
                    action = new PacmanStatusAction(PacmanStatus.NORMAL);
                    action.apply(state);
                    actions.add(action);
                }
            }
            else if (me instanceof Ghost) {
                Ghost ghost = (Ghost)me;
                if (ghost.getStatus() == GhostStatus.FLEE) {
                    action = new GhostStatusAction(charIndex,GhostStatus.TRACK);
                    action.apply(state);
                    actions.add(action);
                }
            }
        }
    }
    
    public void toJson(JsonObjectBuilder builder) {
        builder
            .add("type", "UpdateStatus")
            .add("charIndex",charIndex);
    }
    
    public UpdateStatusCommand(JsonObject json) {
        this.charIndex = json.getInt("charIndex");
    }
}
