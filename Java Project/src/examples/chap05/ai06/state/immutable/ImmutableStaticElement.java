/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap05.ai06.state.immutable;

import examples.chap05.ai06.state.StaticElement;
import examples.chap05.ai06.state.mutable.MutableStaticElement;

public class ImmutableStaticElement extends ImmutableElement implements StaticElement {

    public ImmutableStaticElement(MutableStaticElement element) {
        super(element);
    }

}
