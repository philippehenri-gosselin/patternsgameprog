/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.network03.gui.awt;

import examples.chap06.network03.gui.Mouse;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class AWTMouse implements MouseListener, Mouse, MouseMotionListener {

    private boolean[] buttons;

    private int x;

    private int y;
    
    public AWTMouse() {
        buttons = new boolean[4];
    }

    public boolean isButtonPressed(int button) {
        if (button >= buttons.length) {
            return false;
        }
        return buttons[button];
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() <= 3) {
            buttons[e.getButton()] = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() <= 3) {
            buttons[e.getButton()] = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }
}
