/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.stellaris01;

import examples.chap05.stellaris01.mutable.MutableBuilding;
import java.util.ArrayList;

public interface Habitable extends Planet {

    public ArrayList<Building> getBuildings();

    public void setBuildings(ArrayList<MutableBuilding> buildings);
    
}
