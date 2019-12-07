/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.stellaris01;

import examples.chap05.stellaris01.mutable.MutableConnection;
import examples.chap05.stellaris01.mutable.MutableSystem;

public interface Galaxy {

    public MutableSystem createSystem(String name, int x, int y);

    public MutableConnection connectSystems(String name1, String name2, int parsecs);

    public int findSystem(String name);
    
    public int getSystemCount();

    public System getSystem(int index);
    
    public int findConnection(int index1, int index2);
    
    public int getConnectionCount();
    
    public Connection getConnection(int index);
    
}
