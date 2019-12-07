/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.features02.rules.actions;

import examples.chap04.features02.state.Direction;
import examples.chap04.features02.state.Space;
import examples.chap04.features02.state.SpaceTypeId;
import examples.chap04.features02.state.State;
import examples.chap04.features02.state.StaticElement;
import examples.chap04.features02.state.World;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GumAction implements Action {

    private int x;

    private int y;

    private SpaceTypeId type;

    public GumAction(int x, int y, SpaceTypeId type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public void apply(State state) {
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

    public void undo(State state) {
        World world = state.getWorld();
        StaticElement se = world.get(x, y, Direction.NONE);
        if (!(se instanceof Space)) {
            Logger.getLogger("rules").log(Level.SEVERE,"Un espace est attendu");
            return;
        }
        Space space = (Space)se;
        space.setSpaceTypeId(type);
        state.notifyWorldElementChanged(x, y);
        
        int gumCount = state.getGumCount();
        gumCount ++;
        state.setGumCount(gumCount);        
    }
}
