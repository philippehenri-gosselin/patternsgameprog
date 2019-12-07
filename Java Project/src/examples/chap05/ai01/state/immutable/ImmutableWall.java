/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap05.ai01.state.immutable;

import examples.chap05.ai01.state.Wall;
import examples.chap05.ai01.state.WallTypeId;
import examples.chap05.ai01.state.mutable.MutableWall;

public final class ImmutableWall extends ImmutableStaticElement implements Wall {

    public ImmutableWall(MutableWall element) {
        super(element);
    }

    @Override
    public WallTypeId getWallTypeId() {
        return ((MutableWall)element).getWallTypeId();
    }

    @Override
    public void setWallTypeId(WallTypeId wallTypeId) {
        throw new IllegalAccessError();
    }

}
