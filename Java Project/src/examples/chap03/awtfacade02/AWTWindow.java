/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap03.awtfacade02;

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

    public void init(String title) {
        setTitle(title);
        setSize(200, 200);
        setResizable(false);
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent we) {
                closingRequested = true;
            }
        });
        closingRequested = false;
    }

    public void createCanvas() {
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(canvasWidth, canvasHeight));
        canvas.setMinimumSize(new Dimension(canvasWidth, canvasHeight));
        canvas.setMaximumSize(new Dimension(canvasWidth, canvasHeight));
        add(canvas);
        pack();
    }

    public boolean isClosingRequested() {
        return closingRequested;
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

}
