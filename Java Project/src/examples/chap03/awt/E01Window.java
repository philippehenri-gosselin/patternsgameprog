/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap03.awt;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class E01Window extends Frame {
    
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
    
    public static void main(String args[]) {
        E01Window window = new E01Window();
        window.init();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }    
}
