/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.features03.state;

import java.io.Externalizable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.swing.JOptionPane;

public class State implements Serializable {
    
    private int epoch;
    
    private int epochRate = 12;
        
    private Characters chars;

    private World world;
    
    private int gumCount;

    private int superDuration = 100;
    
    private List<StateObserver> observers = new ArrayList();
        
    public State() {
    }
    
    public void init(int[][] level,int width,int height,int pacmanSpeed,int ghostSpeed) {
        world = new World(width,height);
        world.setFactory(ElementFactory.getDefault());
        world.init(level);
        
        gumCount = 0;
        for (StaticElement se : world) {
            if (se instanceof Space) {
                Space space = (Space)se;
                if (space.getSpaceTypeId() == SpaceTypeId.GUM
                 || space.getSpaceTypeId() == SpaceTypeId.SUPERGUM) {
                    gumCount ++;
                }
            }
        }        
        
        chars = new Characters();
        chars.init(level);   
        chars.get(0).setSpeed(pacmanSpeed);
        for (int charIndex=1;charIndex<chars.size();charIndex++) {
            chars.get(charIndex).setSpeed(ghostSpeed);
        }        
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
    
    public void save(String fileName) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("save.ser"))) {
            out.writeObject(this);
        }   
        catch(IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error when saving", "Error", JOptionPane.ERROR_MESSAGE);
        }    
    }
    
    public static State load(String fileName) {
        State state = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("save.ser"))) {
            state = (State) in.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error when loading", "Error", JOptionPane.ERROR_MESSAGE);
        }    
        return state;
    }
    
    private Object writeReplace() {
        return new StateProxy(this);
    }

    private static final long serialVersionUID = 7832472837472347690L;    
    private static class StateProxy implements Externalizable
    {        
        private State state;
        
        public StateProxy() {
        }
        
        public StateProxy(State state) {
            this.state = state;
        }

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeInt(1);
            out.writeInt(0);
            out.writeInt(state.epoch);
            out.writeInt(state.epochRate);
            out.writeInt(state.gumCount);
            out.writeInt(state.superDuration);
            out.writeObject(state.world);
            out.writeObject(state.chars);
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            int versionMaj = in.readInt();
            int versionMin = in.readInt();
            if (versionMaj != 1 && versionMin != 0)
                throw new UnsupportedEncodingException("Unsupported file version");
            state = new State();
            state.epoch = in.readInt();
            state.epochRate = in.readInt();
            state.gumCount = in.readInt();
            state.superDuration = in.readInt();
            state.world = (World)in.readObject();
            state.chars = (Characters)in.readObject();
        }
        
        private Object readResolve() {
            return state;
        }
        
    }
}
