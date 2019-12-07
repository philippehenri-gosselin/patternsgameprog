/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.network01.gui;

public interface Keyboard {

    public boolean isKeyPressed(int keyCode);
    
    public int getLastPressedKey();
    
    public int consumeLastPressedKey();
}
