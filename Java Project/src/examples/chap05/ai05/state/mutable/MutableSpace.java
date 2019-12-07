/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap05.ai05.state.mutable;

import examples.chap05.ai05.state.Element;
import examples.chap05.ai05.state.Space;
import examples.chap05.ai05.state.SpaceTypeId;
import examples.chap05.ai05.state.immutable.ImmutableSpace;

public class MutableSpace extends MutableStaticElement implements Space {

    private SpaceTypeId spaceTypeId;

    public MutableSpace(SpaceTypeId id) {
        this.spaceTypeId = id;
    }

    @Override
    public SpaceTypeId getSpaceTypeId() {
        return spaceTypeId;
    }

    @Override
    public void setSpaceTypeId(SpaceTypeId spaceTypeId) {
        this.spaceTypeId = spaceTypeId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MutableSpace other = (MutableSpace) obj;
        if (this.spaceTypeId != other.spaceTypeId) {
            return false;
        }
        return true;
    }

    @Override
    public Element toImmutable() {
        return new ImmutableSpace(this);
    }
    
}
