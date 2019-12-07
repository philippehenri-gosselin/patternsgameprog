/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.command03.state;

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
}
