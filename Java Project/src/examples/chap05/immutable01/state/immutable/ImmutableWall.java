/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap05.immutable01.state.immutable;

import examples.chap05.immutable01.state.Wall;
import examples.chap05.immutable01.state.WallTypeId;

public class ImmutableWall extends Wall {

    public ImmutableWall(Wall wall) {
        super(wall.getWallTypeId());
    }

    public void setWallTypeId(WallTypeId wallTypeId) {
        throw new IllegalAccessError();
    }

}
