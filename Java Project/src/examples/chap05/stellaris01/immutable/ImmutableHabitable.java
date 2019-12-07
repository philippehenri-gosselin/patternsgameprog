/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.stellaris01.immutable;

import examples.chap05.stellaris01.Building;
import examples.chap05.stellaris01.Habitable;
import examples.chap05.stellaris01.mutable.MutableBuilding;
import examples.chap05.stellaris01.mutable.MutableHabitable;
import java.util.ArrayList;

public final class ImmutableHabitable extends ImmutablePlanet implements Habitable {

    public ImmutableHabitable(MutableHabitable planet) {
        super(planet);
    }

    public ArrayList<Building> getBuildings() {
        ArrayList<Building> list = new ArrayList();
        MutableHabitable habitable = (MutableHabitable)planet;
        for (Building building : habitable.getBuildings()) {
            list.add(building.toImmutable());
        }
        return list;
    }

    public void setBuildings(ArrayList<MutableBuilding> buildings) {
        throw new IllegalAccessError();
    }

    public ImmutablePlanet toImmutable() {
        return this;
    }
    
}
