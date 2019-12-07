/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package pacman.state.immutable;

import pacman.state.Element;
import pacman.state.mutable.MutableElement;

public class ImmutableElement implements Element {

    protected final MutableElement element;
    
    public ImmutableElement(MutableElement element) {
        this.element = element;
    }

    @Override
    public Element clone() {
        return element.clone();
    }

    @Override
    public Element toImmutable() {
        return this;
    }
}
