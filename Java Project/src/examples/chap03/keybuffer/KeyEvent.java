/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap03.keybuffer;

public interface KeyEvent {

    public int getKeyCode();

    public KeyEventType getType();

    public long getKeyTime();
}
