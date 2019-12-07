/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap05.immutable01.state.immutable;

import examples.chap05.immutable01.state.Characters;
import examples.chap05.immutable01.state.State;
import examples.chap05.immutable01.state.StateObserver;
import examples.chap05.immutable01.state.World;

public class ImmutableState extends State {
        
    public ImmutableState(State state) {
        this.chars = new ImmutableCharacters(state.getChars());
        this.epoch = state.getEpoch();
        this.epochRate = state.getEpochRate();
        this.gumCount = state.getGumCount();
        this.superDuration = state.getSuperDuration();
        this.world = new ImmutableWorld(state.getWorld());
    }
    
    public void init(int[][] level,int width,int height,int pacmanSpeed,int ghostSpeed) {
        throw new IllegalAccessError();     
    }

    public void setEpochRate(int epochRate) {
        throw new IllegalAccessError();
    }
    
    public void setChars(Characters chars) {
        throw new IllegalAccessError();
    }

    public void setWorld(World world) {
        throw new IllegalAccessError();
    }

    public void registerObserver(StateObserver observer) {
        throw new IllegalAccessError();
    }
    
    public void unregisterObserver(StateObserver observer) {
        throw new IllegalAccessError();
    }

    public void incEpoch() {
        throw new IllegalAccessError();
    }
    
    public void decEpoch() {
        throw new IllegalAccessError();
    }
    
    public void setEpoch(int epoch) {
        throw new IllegalAccessError();
    }
    
    public void setGumCount(int gumCount) {
        throw new IllegalAccessError();
    }
    
    public void setSuperDuration(int superDuration) {
        throw new IllegalAccessError();
    }
    
    private Object writeReplace() {
        throw new IllegalAccessError();
    }
}
