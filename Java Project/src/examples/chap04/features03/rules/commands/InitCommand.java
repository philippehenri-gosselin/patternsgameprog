/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.features03.rules.commands;

import examples.chap04.features03.state.State;
import examples.chap04.features03.rules.actions.Action;
import java.util.Queue;

public class InitCommand implements Command {

    private int pacmanSpeed = 2;
    
    private int ghostSpeed = 2;
    
    static final int[][] level = new int[][] {
        { 15,11,11,11,11,11,11,11,16 },
        { 12,5,3,3,3,3,3,3,12 },
        { 12,3,15,11,11,11,16,3,12 },
        { 14,3,13,11,11,11,14,3,13 },
        { 21,3,3,3,25,24,22,23,3 },
        { 11,11,11,11,11,11,11,11,11 }
    };    

    public InitCommand(int pacmanSpeed,int ghostSpeed) {
        this.pacmanSpeed = pacmanSpeed;
        this.ghostSpeed = ghostSpeed;
    }   
    
    public void execute(Queue<Action> actions,State state) {
        state.init(level,9,6,pacmanSpeed,ghostSpeed);
        state.notityStateChanged();
    }
    
}
