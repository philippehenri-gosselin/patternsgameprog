/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.features03.state;

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
        final Wall other = (Wall) obj;
        if (this.wallTypeId != other.wallTypeId) {
            return false;
        }
        return true;
    }
    
    
}
