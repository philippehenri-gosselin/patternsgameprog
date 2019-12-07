/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.network02.state;

public interface Wall extends StaticElement {

    public WallTypeId getWallTypeId();

    public void setWallTypeId(WallTypeId wallTypeId);

    public boolean equals(Object obj);
}
