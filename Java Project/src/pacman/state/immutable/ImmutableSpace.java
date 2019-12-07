/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package pacman.state.immutable;

import pacman.state.Space;
import pacman.state.SpaceTypeId;
import pacman.state.mutable.MutableSpace;

public final class ImmutableSpace extends ImmutableStaticElement implements Space {

    public ImmutableSpace(MutableSpace element) {
        super(element);
    }

    @Override
    public SpaceTypeId getSpaceTypeId() {
        return ((MutableSpace)element).getSpaceTypeId();
    }

    @Override
    public void setSpaceTypeId(SpaceTypeId spaceTypeId) {
        throw new IllegalAccessError();
    }

}
