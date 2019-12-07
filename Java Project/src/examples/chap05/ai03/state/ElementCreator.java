/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.ai03.state;

import examples.chap05.ai03.state.mutable.MutableElement;

public interface ElementCreator {

    public MutableElement create();
}
