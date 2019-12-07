/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap05.ai05.state.mutable;

import examples.chap05.ai05.state.Element;
import examples.chap05.ai05.state.Ghost;
import examples.chap05.ai05.state.GhostStatus;
import examples.chap05.ai05.state.immutable.ImmutableGhost;

public class MutableGhost extends MutableMobileElement implements Ghost {
    
    protected GhostStatus status = GhostStatus.TRACK;

    private int color;

    public MutableGhost(int color) {
        this.color = color;
    }

    @Override
    public GhostStatus getStatus() {
        return status;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public void setStatus(GhostStatus status) {
        this.status = status;
    }

    @Override
    public void setColor(int color) {
        this.color = color;
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
        final MutableGhost other = (MutableGhost) obj;
        if (this.color != other.color) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        return true;
    }

    @Override
    public Element toImmutable() {
        return new ImmutableGhost(this);
    }

}
