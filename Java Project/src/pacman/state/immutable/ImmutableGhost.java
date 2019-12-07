/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package pacman.state.immutable;

import pacman.state.Ghost;
import pacman.state.GhostStatus;
import pacman.state.mutable.MutableGhost;

public class ImmutableGhost extends ImmutableMobileElement implements Ghost {

    public ImmutableGhost(MutableGhost element) {
        super(element);
    }

    @Override
    public int getColor() {
        return ((MutableGhost)element).getColor();
    }

    @Override
    public void setColor(int color) {
        throw new IllegalAccessError();
    }

    @Override
    public GhostStatus getStatus() {
        return ((MutableGhost)element).getStatus();
    }

    @Override
    public void setStatus(GhostStatus status) {
        throw new IllegalAccessError();
    }

}
