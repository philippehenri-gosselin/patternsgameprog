/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap05.ai02.state.mutable;

import examples.chap05.ai02.state.Element;
import examples.chap05.ai02.state.Wall;
import examples.chap05.ai02.state.WallTypeId;
import examples.chap05.ai02.state.immutable.ImmutableWall;

public class MutableWall extends MutableStaticElement implements Wall {
    
    private WallTypeId wallTypeId;

    public MutableWall(WallTypeId id) {
        this.wallTypeId = id;
    }

    @Override
    public WallTypeId getWallTypeId() {
        return wallTypeId;
    }

    @Override
    public void setWallTypeId(WallTypeId wallTypeId) {
        this.wallTypeId = wallTypeId;
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
        final MutableWall other = (MutableWall) obj;
        if (this.wallTypeId != other.wallTypeId) {
            return false;
        }
        return true;
    }

    @Override
    public Element toImmutable() {
        return new ImmutableWall(this);
    }

}
