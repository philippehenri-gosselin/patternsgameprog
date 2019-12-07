/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap03.awt;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

public class E04DisplaySync extends Frame {
    
    private int canvasWidth = 800;
    private int canvasHeight = 600;
    private Canvas canvas;
    private boolean running = true;
    
    public void init() {
        setTitle("Display and controls with AWT");
        setSize(200,200);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                running = false;
            }
        });        
    }
    
    public void createCanvas() {
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(canvasWidth,canvasHeight));
        canvas.setMinimumSize(new Dimension(canvasWidth,canvasHeight));
        canvas.setMaximumSize(new Dimension(canvasWidth,canvasHeight));
        add(canvas);
        pack();
    }

    public void render() {
        BufferStrategy bs = canvas.getBufferStrategy();
        if (bs == null) {
            canvas.createBufferStrategy(2);
            return;
        }
        Graphics g = null;
        try {
            g = bs.getDrawGraphics();

            g.setColor(Color.black);
            g.fillRect(0,0,canvasWidth,canvasHeight);       
            
            bs.show();
        }
        finally {
            if (g != null) {
                g.dispose();
            }
        }
    }
    
    public void run() 
    {        
        int fps = 60;
        long nanoPerFrame = (long) (1000000000.0 / fps);
        long lastTime = 0;

        while (running) {
            long nowTime = System.nanoTime();
            if ((nowTime-lastTime) < nanoPerFrame) {
                continue;
            }
            lastTime = nowTime;

            render();

            long elapsed = System.nanoTime() - lastTime;
            long milliSleep = (nanoPerFrame - elapsed) / 1000000;
            if (milliSleep > 0) {
                try {
                    Thread.sleep (milliSleep);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }            
        }
        dispose();
    }
    
    public static void main(String args[]) {
        E04DisplaySync window = new E04DisplaySync();
        window.init();
        window.createCanvas();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.run();
    }    
}
