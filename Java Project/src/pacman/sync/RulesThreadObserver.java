/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.sync;

import pacman.rules.Rules;

public interface RulesThreadObserver {

    public void stateUpdating(Rules rules);
    
    public void stateUpdated(Rules rules);
    
    public void gameOver(Rules rules);
    
}
