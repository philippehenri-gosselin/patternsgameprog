/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap02.stellaris;

import java.util.ArrayList;

public class Habitable extends Planet {

    private ArrayList<Building> buildings;

    public Habitable(String name) {
        super(name);
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(ArrayList<Building> buildings) {
        this.buildings = buildings;
    }
}
