/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap03.keybuffer;

import java.util.List;

public interface Keyboard {

    public boolean isKeyPressed(int keyCode);

    public void setMaxKeyEventLifeTime(int time);

    public List<KeyEvent> getKeyEvents();

    public void clearKeyEvents();
}
