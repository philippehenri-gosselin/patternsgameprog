/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.stellaris01.immutable;

import examples.chap05.stellaris01.Building;
import examples.chap05.stellaris01.BuildingType;
import examples.chap05.stellaris01.mutable.MutableBuilding;

public final class ImmutableBuilding implements Building {

    private final MutableBuilding building;

    public ImmutableBuilding(MutableBuilding building) {
        this.building = building;
    }
    
    public BuildingType getType() {
        return building.getType();
    }

    public int getLevel() {
        return building.getLevel();
    }

    public void setType(BuildingType type) {
        throw new IllegalAccessError();
    }

    public void setLevel(int level) {
        throw new IllegalAccessError();
    }

    public ImmutableBuilding toImmutable() {
        return this;
    }
}
