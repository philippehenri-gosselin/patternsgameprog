/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.draw05.state;

public interface StateObserver {

    public void worldElementChanged(State state, int x, int y);

    public void characterChanged(State state, int charIndex);
}
