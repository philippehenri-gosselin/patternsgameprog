/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.threads01.state;

import examples.chap06.threads01.state.mutable.MutableElement;

public interface ElementCreator {

    public MutableElement create();
}
