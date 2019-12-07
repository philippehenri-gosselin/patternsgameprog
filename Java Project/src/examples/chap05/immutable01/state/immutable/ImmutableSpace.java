/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap05.immutable01.state.immutable;

import examples.chap05.immutable01.state.Space;
import examples.chap05.immutable01.state.SpaceTypeId;

public class ImmutableSpace extends Space {

    public ImmutableSpace(Space space) {
        super(space.getSpaceTypeId());
    }
    
    public void setSpaceTypeId(SpaceTypeId spaceTypeId) {
        throw new IllegalAccessError();                
    }    
}
