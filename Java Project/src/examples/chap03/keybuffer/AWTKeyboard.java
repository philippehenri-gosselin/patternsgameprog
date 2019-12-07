/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap03.keybuffer;

import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class AWTKeyboard implements Keyboard, KeyListener {

    private boolean[] keys;
    
    private LinkedList<KeyEvent> keyEvents = new LinkedList();
    
    private long maxKeyEventLifeTime;
    
    public AWTKeyboard() {
        maxKeyEventLifeTime = (long)(3 * 1000000000.0);
        keys = new boolean[java.awt.event.KeyEvent.KEY_LAST + 1];
    }

    public boolean isKeyPressed(int keyCode) {
        if (keyCode >= keys.length) {
            return false;
        }
        return keys[keyCode];
    }

    public void setMaxKeyEventLifeTime(int time) {
        this.maxKeyEventLifeTime = time;
    }

    public List<KeyEvent> getKeyEvents() {
        long now = System.nanoTime();
        Iterator<KeyEvent> iterator = keyEvents.iterator();
        while (iterator.hasNext()) {
            KeyEvent event = iterator.next();
            if ( (now - event.getKeyTime()) > maxKeyEventLifeTime ) {
                iterator.remove();
            }
        }
        return new ArrayList<KeyEvent>(keyEvents);
    }

    public void clearKeyEvents() {
        keyEvents.clear();
    }
    
    public void keyTyped(java.awt.event.KeyEvent e) {
    }
    
    public void keyPressed(java.awt.event.KeyEvent e) {
        if (e.getKeyCode() < keys.length) {
            keys[e.getKeyCode()] = true;
        }
        keyEvents.add(new AWTKeyEvent(e.getKeyCode(),KeyEventType.KEY_PRESSED,System.nanoTime()));
    }
    
    public void keyReleased(java.awt.event.KeyEvent e) {
        if (e.getKeyCode() < keys.length) {
            keys[e.getKeyCode()] = false;
        }
        keyEvents.add(new AWTKeyEvent(e.getKeyCode(),KeyEventType.KEY_RELEASED,System.nanoTime()));
    }

}
