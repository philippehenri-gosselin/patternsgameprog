/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.threads01.ai.tree.pacman;

import examples.chap06.threads01.ai.AI;
import examples.chap06.threads01.ai.CommandsLister;
import examples.chap06.threads01.ai.TrackAI;
import examples.chap06.threads01.ai.mapproviders.CharMapMaintainer;
import examples.chap06.threads01.ai.mapproviders.DistanceMapProvider;
import examples.chap06.threads01.rules.Rules;
import examples.chap06.threads01.state.Characters;
import examples.chap06.threads01.state.State;
import java.util.Random;

public final class Parameters {
    
    public final Rules rules;
    
    public final State state;

    public final CommandsLister lister;
    
    public final Random random = new Random();
    
    public final DistanceMapProvider dmProvider;
    
    public final AI[] ais;
    
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
