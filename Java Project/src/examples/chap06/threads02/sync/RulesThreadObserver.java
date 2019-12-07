/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.threads02.sync;

import examples.chap06.threads02.rules.Rules;

public interface RulesThreadObserver {

    public void stateUpdating(Rules rules);
    
    public void stateUpdated(Rules rules);
    
    public void gameOver(Rules rules);
    
}
