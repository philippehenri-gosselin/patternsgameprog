/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap05.ai04.state.immutable;

import examples.chap05.ai04.state.Ghost;
import examples.chap05.ai04.state.GhostStatus;
import examples.chap05.ai04.state.mutable.MutableGhost;

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
