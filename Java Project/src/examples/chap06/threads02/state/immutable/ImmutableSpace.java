/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap06.threads02.state.immutable;

import examples.chap06.threads02.state.Space;
import examples.chap06.threads02.state.SpaceTypeId;
import examples.chap06.threads02.state.mutable.MutableSpace;

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
