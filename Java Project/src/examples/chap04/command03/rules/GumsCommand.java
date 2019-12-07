/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.command03.rules;

import examples.chap04.command03.state.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GumsCommand implements Command {

    private void eatGum(State state, int x, int y) {
        World world = state.getWorld();
        StaticElement se = world.get(x, y, Direction.NONE);
        if (!(se instanceof Space)) {
            Logger.getLogger("rules").log(Level.SEVERE,"Un espace est attendu");
            return;
        }
        Space space = (Space)se;
        if ((space.getSpaceTypeId() != SpaceTypeId.GUM)
          && space.getSpaceTypeId() != SpaceTypeId.SUPERGUM) {
            Logger.getLogger("rules").log(Level.SEVERE,"Une pastille est attendue");
            return;
        }
        space.setSpaceTypeId(SpaceTypeId.EMPTY);
        state.notifyWorldElementChanged(x, y);
        int gumCount = state.getGumCount();
        if (gumCount <= 0) {
            Logger.getLogger("rules").log(Level.SEVERE,"Erreur dans le comptage des Gums");
            return;
        }
        gumCount --;
        state.setGumCount(gumCount);
    }

    private void enableSuper(State state) {
        Characters chars = state.getChars();
        Pacman pacman = chars.getPacman();
        if (pacman == null) {
            Logger.getLogger("rules").log(Level.SEVERE,"No Pacman");
            return;
        }
        pacman.setStatus(PacmanStatus.SUPER);
        pacman.setStatusTime(state.getSuperDuration());
        state.notifyCharacterChanged(0);
        
        for (int index=1;index<chars.size();index++) {
            Ghost ghost = chars.getGhost(index);
            if (ghost == null) {
                Logger.getLogger("rules").log(Level.SEVERE,"No ghost");
                continue;
            }
            ghost.setStatusTime(state.getSuperDuration());
            if (ghost.getStatus() != GhostStatus.EYES) {
                ghost.setStatus(GhostStatus.FLEE);
            }
            state.notifyCharacterChanged(index);
        }
        
    }

    public void execute(State state) {
        Characters chars = state.getChars();
        Pacman pacman = chars.getPacman();
        if (pacman == null) {
            Logger.getLogger("rules").log(Level.SEVERE,"No Pacman");
            return;
        }
        if (pacman.getStatus() == PacmanStatus.DEAD)
            return;
        if (pacman.getPosition() == 0) {
            int x = pacman.getX();
            int y = pacman.getY();
            World world = state.getWorld();
            StaticElement se = world.get(x, y, Direction.NONE);
            if (se instanceof Space) {
                Space space = (Space)se;
                SpaceTypeId type = space.getSpaceTypeId();
                if (type == SpaceTypeId.GUM
                 || type == SpaceTypeId.SUPERGUM) {
                    eatGum(state,x,y);
                    if (type == SpaceTypeId.SUPERGUM) {
                        enableSuper(state);
                    }
                }
            }
        }        
    }
}
