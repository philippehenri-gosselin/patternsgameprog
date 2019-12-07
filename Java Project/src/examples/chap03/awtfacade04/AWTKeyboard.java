/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap03.awtfacade04;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class AWTKeyboard implements Keyboard, KeyListener {

    private boolean[] keys;

    public AWTKeyboard() {
        keys = new boolean[0x10000];
    }

    public boolean isKeyPressed(int keyCode) {
        if (keyCode >= keys.length) {
            return false;
        }
        return keys[keyCode];
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() < keys.length) {
            keys[e.getKeyCode()] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() < keys.length) {
            keys[e.getKeyCode()] = false;
        }
    }

}
