/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.state;

import pacman.state.mutable.MutableElement;

public interface ElementCreator {

    public MutableElement create();
}
