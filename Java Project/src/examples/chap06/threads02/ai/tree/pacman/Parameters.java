/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.threads02.ai.tree.pacman;

import examples.chap06.threads02.ai.AI;
import examples.chap06.threads02.ai.CommandsLister;
import examples.chap06.threads02.ai.TrackAI;
import examples.chap06.threads02.ai.mapproviders.CharMapMaintainer;
import examples.chap06.threads02.ai.mapproviders.DistanceMapProvider;
import examples.chap06.threads02.rules.Rules;
import examples.chap06.threads02.state.Characters;
import examples.chap06.threads02.state.State;
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
    
    public int maxUpdateCount = 1000;
    
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
