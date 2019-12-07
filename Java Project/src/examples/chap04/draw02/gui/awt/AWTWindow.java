/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.draw02.gui.awt;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

public class AWTWindow extends Frame {

    private boolean closingRequested;

    private Canvas canvas;

    private int canvasWidth = 800;

    private int canvasHeight = 600;

    private BufferStrategy bs;
    
    private AWTKeyboard keyboard;
    
    private AWTMouse mouse;

    public void init(String title) {
        setTitle(title);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                closingRequested = true;
            }
        });
        addWindowFocusListener(new WindowAdapter() {
            public void windowGainedFocus(WindowEvent e) {
                if (canvas != null) {
                    canvas.requestFocusInWindow();
                }
            }
        });
        closingRequested = false;
    }

    public void createCanvas(int width, int height) {
        if (canvas == null) {
            canvas = new Canvas();
            add(canvas);
        }
        if (canvasWidth != width || canvasHeight != height ) {
            setVisible(false);
            canvasWidth = width;
            canvasHeight = height;
            canvas.setPreferredSize(new Dimension(canvasWidth, canvasHeight));
            canvas.setMinimumSize(new Dimension(canvasWidth, canvasHeight));
            canvas.setMaximumSize(new Dimension(canvasWidth, canvasHeight));
            pack();
        }
        
        if (keyboard == null) {
            keyboard = new AWTKeyboard();
            canvas.addKeyListener(keyboard);
        }
        
        if (mouse == null) {
            mouse = new AWTMouse();
            canvas.addMouseListener(mouse);
            canvas.addMouseMotionListener(mouse);
        }
    }

    public boolean isClosingRequested() {
        return closingRequested;
    }

    public void setClosingRequested(boolean closingRequested) {
        this.closingRequested = closingRequested;
    }    
    
    public Graphics createGraphics() {
        bs = canvas.getBufferStrategy();
        if (bs == null) {
            canvas.createBufferStrategy(2);
            return null;
        }
        return bs.getDrawGraphics();
    }

    public int getCanvasWidth() {
        return canvasWidth;
    }

    public int getCanvasHeight() {
        return canvasHeight;
    }

    public void switchBuffers() {
        if (bs != null) {
            bs.show();
        }
    }

    public AWTKeyboard getKeyboard() {
        return keyboard;
    }

    public AWTMouse getMouse() {
        return mouse;
    }  
    
}
