/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap06.network03.state.mutable;

import examples.chap06.network03.state.ElementFactory;
import examples.chap06.network03.state.Characters;
import examples.chap06.network03.state.Space;
import examples.chap06.network03.state.SpaceTypeId;
import examples.chap06.network03.state.State;
import examples.chap06.network03.state.StateObserver;
import examples.chap06.network03.state.StaticElement;
import examples.chap06.network03.state.World;
import examples.chap06.network03.state.immutable.ImmutableState;
import java.io.Externalizable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.swing.JOptionPane;

public class MutableState implements State, Serializable
{
    protected int epoch;
    
    protected int epochRate = 12;
        
    protected MutableCharacters chars;

    protected MutableWorld world;
    
    protected int gumCount;

    protected int superDuration = 100;
    
    private List<StateObserver> observers = new ArrayList();
    
    @Override
    public void init(int[][] level,int width,int height,int pacmanSpeed,int ghostSpeed) {
        world = new MutableWorld(width,height);
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
        
        chars = new MutableCharacters();
        chars.init(level);   
        chars.get(0).setSpeed(pacmanSpeed);
        for (int charIndex=1;charIndex<chars.size();charIndex++) {
            chars.get(charIndex).setSpeed(ghostSpeed);
        }        
    }

    @Override
    public Characters getChars() {
        return chars;
    }

    @Override
    public World getWorld() {
        return world;
    }
    
    @Override
    public long getEpochDuration() {
        return 1000000000 / epochRate;
    }    

    @Override
    public int getEpochRate() {
        return epochRate;
    }
    
    @Override
    public void setEpochRate(int epochRate) {
        this.epochRate = epochRate;
    }
    
    @Override
    public void setChars(Characters chars) {
        if (!(chars instanceof MutableCharacters))
            throw new IllegalArgumentException("Un élément modifiable est attendu");
        this.chars = (MutableCharacters)chars;
    }

    @Override
    public void setWorld(World world) {
        if (!(world instanceof MutableWorld))
            throw new IllegalArgumentException("Un élément modifiable est attendu");
        this.world = (MutableWorld)world;
    }

    @Override
    public void registerObserver(StateObserver observer) {
        observers.add(observer);
    }
    
    @Override
    public void unregisterObserver(StateObserver observer) {
        observers.remove(observer);
    }
    
    @Override
    public void notityStateChanged() {
        ImmutableState roState = new ImmutableState(this);
        for (StateObserver observer : observers) {
            observer.stateChanged(roState);
        }
    }
    
    @Override
    public void notifyWorldElementChanged(int x, int y) {
        ImmutableState roState = new ImmutableState(this);
        for (StateObserver observer : observers) {
            observer.worldElementChanged(roState, x, y);
        }
    }
    
    @Override
    public void notifyCharacterChanged(int charIndex) {
        ImmutableState roState = new ImmutableState(this);
        for (StateObserver observer : observers) {
            observer.characterChanged(roState, charIndex);
        }
    }

    @Override
    public void incEpoch() {
        epoch ++;
    }
    
    @Override
    public void decEpoch() {
        epoch --; 
    }
    
    @Override
    public int getEpoch() {
        return epoch;
    }
    
    @Override
    public void setEpoch(int epoch) {
    this.epoch = epoch;
    }

    @Override
    public int getGumCount() {
        return gumCount;
    }
    
    @Override
    public void setGumCount(int gumCount) {
        this.gumCount = gumCount;
    }
    
    @Override
    public int getSuperDuration() {
        return superDuration;
    }   
    
    @Override
    public void setSuperDuration(int superDuration) {
        this.superDuration = superDuration;
    }
    
    @Override
    public State clone() {
        MutableState other = new MutableState();
        other.chars = (MutableCharacters)chars.clone();
        other.epoch = epoch;
        other.epochRate = epochRate;
        other.gumCount = gumCount;
        other.superDuration = superDuration;
        other.world = (MutableWorld)world.clone();
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
        final MutableState other = (MutableState) obj;
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
    
    private Object writeReplace() {
        return new StateProxy(this);
    }

    private static final long serialVersionUID = 7832472837472347690L;    
    private static class StateProxy implements Externalizable
    {        
        private MutableState state;
        
        public StateProxy() {
        }
        
        public StateProxy(MutableState state) {
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
            state = new MutableState();
            state.epoch = in.readInt();
            state.epochRate = in.readInt();
            state.gumCount = in.readInt();
            state.superDuration = in.readInt();
            state.world = (MutableWorld)in.readObject();
            state.chars = (MutableCharacters)in.readObject();
        }
        
        private Object readResolve() {
            return state;
        }
        
    }

}
