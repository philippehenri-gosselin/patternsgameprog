/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.command01.state;

import java.util.ArrayList;
import java.util.List;

public class State {
    
    private int epoch;
    
    private int epochRate = 12;
        
    private Characters chars;

    private World world;
    
    private List<StateObserver> observers = new ArrayList();
        

    public State() {
    }

    public Characters getChars() {
        return chars;
    }

    public World getWorld() {
        return world;
    }
    
    public long getEpochDuration() {
        return 1000000000 / epochRate;
    }    

    public int getEpochRate() {
        return epochRate;
    }
    
    public void setEpochRate(int epochRate) {
        this.epochRate = epochRate;
    }
    
    public void setChars(Characters chars) {
        this.chars = chars;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void registerObserver(StateObserver observer) {
        observers.add(observer);
    }
    
    public void unregisterObserver(StateObserver observer) {
        observers.remove(observer);
    }
    
    public void notityStateChanged() {
        for (StateObserver observer : observers) {
            observer.stateChanged(this);
        }
    }
    
    public void notifyWorldElementChanged(int x, int y) {
        for (StateObserver observer : observers) {
            observer.worldElementChanged(this, x, y);
        }
    }
    
    public void notifyCharacterChanged(int charIndex) {
        for (StateObserver observer : observers) {
            observer.characterChanged(this, charIndex);
        }
    }

    public void incEpoch() {
        epoch ++;
    }
    
    public int getEpoch() {
        return epoch;
    }
    
    public void setEpoch(int epoch) {
    this.epoch = epoch;
    }


}
