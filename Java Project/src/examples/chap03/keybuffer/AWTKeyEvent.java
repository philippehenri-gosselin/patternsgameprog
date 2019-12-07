/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap03.keybuffer;

public class AWTKeyEvent implements KeyEvent {

    private int keyCode;
    
    private KeyEventType type;
    
    private long time;
    
    public AWTKeyEvent(int keyCode, KeyEventType type, long time) {
        this.keyCode = keyCode;
        this.type = type;
        this.time = time;
    }
    
    public int getKeyCode() {
        return keyCode;
    }

    public KeyEventType getType() {
        return type;
    }

    public long getKeyTime() {
        return time;
    }

}
