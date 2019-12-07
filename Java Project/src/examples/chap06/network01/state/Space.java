/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.network01.state;

public interface Space extends StaticElement {

    public SpaceTypeId getSpaceTypeId();

    public void setSpaceTypeId(SpaceTypeId spaceTypeId);

    public boolean equals(Object obj);
}
