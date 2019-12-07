/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap05.connect4;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;

public class BoardComponent extends JComponent {
    
    private Board board;
    
    public BoardComponent(Board board) {
        this.board = board;
        setMinimumSize(new Dimension(70 * 8, 60 * 8));
        setPreferredSize(new Dimension(70 * 8, 60 * 8));
        setMaximumSize(new Dimension(70 * 8, 60 * 8));
    }

    @Override
    public void paint(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        g.setColor(new Color(0,0,128));
        g.fillRect(0, 0, width, height);
        for (int j = 0; j < 6; j++) {
            for (int i = 0; i < 7; i++) {
                int p = board.getCell(i, j);
                if (p != 0) {
                    if (p > 0) {
                        g.setColor(Color.RED);
                    } else {
                        g.setColor(Color.YELLOW);
                    }
                    g.fillOval(
                        (i * width)/7 + 5, 
                        (j * height)/6 + 5, 
                        width/7 - 10,
                        height/6 - 10
                    );
                }
            }
        }
    }
}
