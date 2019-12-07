/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.stellaris01.mutable;

import examples.chap05.stellaris01.Building;
import examples.chap05.stellaris01.Habitable;
import examples.chap05.stellaris01.immutable.ImmutableHabitable;
import examples.chap05.stellaris01.immutable.ImmutablePlanet;
import java.util.ArrayList;

public class MutableHabitable extends MutablePlanet implements Habitable {

    private ArrayList<MutableBuilding> buildings;

    public MutableHabitable(String name) {
        super(name);
    }

    public ArrayList<Building> getBuildings() {
        return (ArrayList)buildings;
    }

    public void setBuildings(ArrayList<MutableBuilding> buildings) {
        this.buildings = buildings;
    }

    public ImmutablePlanet toImmutable() {
        return new ImmutableHabitable(this);
    }
}
