/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.draw01.state;

public class Wall extends StaticElement {

    private WallTypeId wallTypeId;

    public Wall(WallTypeId id) {
        this.wallTypeId = id;
    }

    public WallTypeId getWallTypeId() {
        return wallTypeId;
    }

    public void setWallTypeId(WallTypeId wallTypeId) {
        this.wallTypeId = wallTypeId;
    }
}
