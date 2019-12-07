/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.stellaris01.mutable;

import examples.chap05.stellaris01.Building;
import examples.chap05.stellaris01.BuildingType;
import examples.chap05.stellaris01.immutable.ImmutableBuilding;

public class MutableBuilding implements Building {

    private BuildingType type;

    private int level;

    public MutableBuilding(BuildingType type, int level) {
        this.type = type;
        this.level = level;
    }

    public BuildingType getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public void setType(BuildingType type) {
        this.type = type;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ImmutableBuilding toImmutable() {
        return new ImmutableBuilding(this);
    }
}
