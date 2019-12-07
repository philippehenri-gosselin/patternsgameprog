/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.command04.rules.commands;

import examples.chap04.command04.state.Characters;
import examples.chap04.command04.state.Direction;
import examples.chap04.command04.state.ElementFactory;
import examples.chap04.command04.state.Space;
import examples.chap04.command04.state.SpaceTypeId;
import examples.chap04.command04.state.State;
import examples.chap04.command04.state.StaticElement;
import examples.chap04.command04.state.World;
import examples.chap04.command04.rules.actions.Action;
import java.util.Queue;

public class InitCommand implements Command {

    static final int[][] level = new int[][] {
        { 15,11,11,11,11,11,11,11,16 },
        { 12,5,3,3,3,3,3,3,12 },
        { 12,3,15,11,11,11,16,3,12 },
        { 14,3,13,11,11,11,14,3,13 },
        { 21,3,3,3,25,24,22,23,3 },
        { 11,11,11,11,11,11,11,11,11 }
    };    
    
    public void execute(Queue<Action> actions,State state) {
        World world = new World(9,6);
        world.setFactory(ElementFactory.getDefault());
        world.init(level);
        
        int gumCount = 0;
        for (StaticElement se : world) {
            if (se instanceof Space) {
                Space space = (Space)se;
                if (space.getSpaceTypeId() == SpaceTypeId.GUM
                 || space.getSpaceTypeId() == SpaceTypeId.SUPERGUM) {
                    gumCount ++;
                }
            }
        }
        state.setGumCount(gumCount);
        
        Characters chars = new Characters();
        chars.init(level);
        
        state.setWorld(world);
        state.setChars(chars);    
        
        state.notityStateChanged();
    }
    
}
