/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.checkers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;

public class BoardComponent extends JComponent {
    
    private Board board;
    
    public BoardComponent(Board board) {
        this.board = board;
        setMinimumSize(new Dimension(80 * 8, 80 * 8));
        setPreferredSize(new Dimension(80 * 8, 80 * 8));
        setMaximumSize(new Dimension(80 * 8, 80 * 8));
    }

    @Override
    public void paint(Graphics g) {
        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; i++) {
                if (((i + j) % 2) == 1) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor(Color.BLACK);
                }
                g.fillRect(i * 80, j * 80, 80, 80);

                int p = board.getPiece(i, j);
                if (p >= 0) {
                    if ((p % 2) == 0) {
                        g.setColor(Color.BLACK);
                    } else {
                        g.setColor(Color.WHITE);
                    }
                    g.fillOval(i * 80 + 5, j * 80 + 5, 70, 70);
                    g.setColor(Color.WHITE);
                    g.drawOval(i * 80 + 5, j * 80 + 5, 70, 70);
                }
            }
        }
    }
}
