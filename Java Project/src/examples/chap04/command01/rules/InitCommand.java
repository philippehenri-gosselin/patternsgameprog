/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.command01.rules;

import examples.chap04.command01.state.Characters;
import examples.chap04.command01.state.ElementFactory;
import examples.chap04.command01.state.State;
import examples.chap04.command01.state.World;

public class InitCommand implements Command {

    static final int[][] level = new int[][] {
        { 15,11,11,11,11,11,11,11,16 },
        { 12,5,3,3,3,3,3,3,12 },
        { 12,3,15,11,11,11,16,3,12 },
        { 14,3,13,11,11,11,14,3,13 },
        { 21,3,3,3,25,24,22,23,3 },
        { 11,11,11,11,11,11,11,11,11 }
    };    
    
    public void execute(State state) {
        World world = new World(9,6);
        world.setFactory(ElementFactory.getDefault());
        world.init(level);
        
        Characters chars = new Characters();
        chars.init(level);
        
        state.setWorld(world);
        state.setChars(chars);    
        
        state.notityStateChanged();
    }
    
}
