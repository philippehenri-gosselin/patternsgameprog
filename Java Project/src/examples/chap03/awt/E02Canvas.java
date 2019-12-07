/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap03.awt;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class E02Canvas extends Frame {
    
    private int canvasWidth = 800;
    private int canvasHeight = 600;
    private Canvas canvas;
    
    public void init() {
        setTitle("Display and controls with AWT");
        setSize(200,200);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
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
    
    public static void main(String args[]) {
        E02Canvas window = new E02Canvas();
        window.init();
        window.createCanvas();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }    
}
