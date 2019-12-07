/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.network01.state;

import examples.chap06.network01.state.mutable.MutableState;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import javax.swing.JOptionPane;

public interface State {

    public int getEpoch();

    public void incEpoch();

    public void decEpoch();

    public void setEpoch(int epoch);

    public int getEpochRate();

    public long getEpochDuration();

    public void setEpochRate(int epochRate);

    public int getSuperDuration();

    public void setSuperDuration(int superDuration);

    public int getGumCount();

    public void setGumCount(int gumCount);

    public Characters getChars();

    public void setChars(Characters chars);

    public World getWorld();

    public void setWorld(World world);

    public void init(int[][] level, int width, int height, int pacmanSpeed, int ghostSpeed);

    public State clone();

    public boolean equals(Object obj);
    
    public void registerObserver(StateObserver observer);
    
    public void unregisterObserver(StateObserver observer);
    
    public void notityStateChanged();
    
    public void notifyWorldElementChanged(int x, int y);
    
    public void notifyCharacterChanged(int charIndex);
    
    public void save(String fileName);
    
    public static MutableState load(String fileName) {
        MutableState state = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("save.ser"))) {
            state = (MutableState) in.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error when loading", "Error", JOptionPane.ERROR_MESSAGE);
        }    
        return state;
    }

}
