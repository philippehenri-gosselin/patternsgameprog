/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.command04.state;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class State {
    
    private int epoch;
    
    private int epochRate = 12;
        
    private Characters chars;

    private World world;
    
    private int gumCount;

    private int superDuration = 100;
    
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
    
    public void decEpoch() {
        epoch --; 
    }
    
    public int getEpoch() {
        return epoch;
    }
    
    public void setEpoch(int epoch) {
    this.epoch = epoch;
    }

    public int getGumCount() {
        return gumCount;
    }
    
    public void setGumCount(int gumCount) {
        this.gumCount = gumCount;
    }
    
    public int getSuperDuration() {
        return superDuration;
    }   
    
    public void setSuperDuration(int superDuration) {
        this.superDuration = superDuration;
    }
    
    @Override
    public State clone() {
        State other = new State();
        other.chars = chars.clone();
        other.epoch = epoch;
        other.epochRate = epochRate;
        other.gumCount = gumCount;
        other.superDuration = superDuration;
        other.world = world.clone();
        return other;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final State other = (State) obj;
        if (this.epoch != other.epoch) {
            return false;
        }
        if (this.epochRate != other.epochRate) {
            return false;
        }
        if (this.gumCount != other.gumCount) {
            return false;
        }
        if (this.superDuration != other.superDuration) {
            return false;
        }
        if (!Objects.equals(this.chars, other.chars)) {
            return false;
        }
        if (!Objects.equals(this.world, other.world)) {
            return false;
        }
        return true;
    }


}
