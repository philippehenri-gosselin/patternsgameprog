/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.stellaris01;

import examples.chap05.stellaris01.immutable.ImmutableBuilding;

public interface Building {

    public BuildingType getType();

    public int getLevel();

    public void setType(BuildingType type);

    public void setLevel(int level);
    
    public ImmutableBuilding toImmutable();
    
}
