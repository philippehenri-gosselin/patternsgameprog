/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.ai.tree.pacman;

import pacman.ai.AI;
import pacman.ai.CommandsLister;
import pacman.ai.TrackAI;
import pacman.ai.mapproviders.CharMapMaintainer;
import pacman.ai.mapproviders.DistanceMapProvider;
import pacman.rules.Rules;
import pacman.state.Characters;
import pacman.state.State;
import java.util.Random;

public final class Parameters {
    
    public final Rules rules;
    
    public final State state;

    public final CommandsLister lister;
    
    public final Random random = new Random();
    
    public final DistanceMapProvider dmProvider;
    
    public final AI[] ais;
    
    public int parallelDepth = 12;
    
    public int minDepth = 20;
    
    public int maxDepth = 40;
    
    public int updateCount;
    
    public int maxUpdateCount = 10000;
    
    public Parameters(Rules rules,CommandsLister lister) {
        this.rules = rules;
        this.state = rules.getState();
        this.lister = lister;
        dmProvider = new DistanceMapProvider();
        dmProvider.registerMaintainer("Pacman", new CharMapMaintainer(0));
        state.registerObserver(dmProvider);
        Characters chars = state.getChars();
        ais = new AI[chars.size()];
        for (int charIndex=1;charIndex<chars.size();charIndex++) {
            ais[charIndex] = new TrackAI("Pacman",state,charIndex,lister,random,dmProvider);
        }
        state.notityStateChanged();
    }
    
}
