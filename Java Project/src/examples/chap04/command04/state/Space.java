/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.command04.state;

public class Space extends StaticElement {

    private SpaceTypeId spaceTypeId;

    public Space(SpaceTypeId id) {
        this.spaceTypeId = id;
    }

    public SpaceTypeId getSpaceTypeId() {
        return spaceTypeId;
    }

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
        final Space other = (Space) obj;
        if (this.spaceTypeId != other.spaceTypeId) {
            return false;
        }
        return true;
    }
    
}
