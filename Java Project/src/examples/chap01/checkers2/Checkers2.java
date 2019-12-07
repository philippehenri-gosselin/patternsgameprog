/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap01.checkers2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Checkers2 extends JFrame implements MouseListener {

    private Board board = new Board();
    private int selectedPiece[];
    private JLabel statusBar;
    
    class BoardComponent extends JComponent {
        
        public BoardComponent() {
            setMinimumSize(new Dimension(80 * 8, 80 * 8));
            setPreferredSize(new Dimension(80 * 8, 80 * 8));
            setMaximumSize(new Dimension(80 * 8, 80 * 8));
        }
        
        @Override
        public void paint(Graphics g) {
            for (int j=0;j<8;j++) {
                for (int i=0;i<8;i++) {
                    if (((i+j)%2) == 1)
                        g.setColor(Color.WHITE);
                    else
                        g.setColor(Color.BLACK);
                    g.fillRect(i*80, j*80, 80, 80);

                    int p = board.getPiece(i,j);
                    if (p >= 0) {
                        if ((p%2) == 0)
                            g.setColor(Color.BLACK);
                        else
                            g.setColor(Color.WHITE);
                        g.fillOval(i*80+5, j*80+5, 70, 70);
                        g.setColor(Color.WHITE);
                        g.drawOval(i*80+5, j*80+5, 70, 70);
                    }
                }
            }
        }        
    }
    
    public Checkers2() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Checkers");
        setLayout(new BorderLayout());
        getContentPane().add(new BoardComponent(),BorderLayout.CENTER); 
        statusBar = new JLabel("Current player");
        getContentPane().add(statusBar,BorderLayout.SOUTH);
        pack();        
        redraw();
        
        addMouseListener(this);
    }
    
    public void redraw() {
        String msg = (board.getCurrentPlayer()==0)?"Black":"White";
        statusBar.setText("Current player: "+msg);
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) {
        int i = e.getX() / 80;
        int j = e.getY() / 80;
        if (board.getPiece(i,j) == board.getCurrentPlayer()) {
            selectedPiece = new int[2];
            selectedPiece[0] = i;
            selectedPiece[1] = j;
        }
        else {
            selectedPiece = null;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) { 
        if (selectedPiece == null) {
            return;
        }
        int i = e.getX() / 80;
        int j = e.getY() / 80;    
        if (board.movePiece(selectedPiece[0], selectedPiece[1], i, j)) {
            redraw();
        }
        selectedPiece = null;
    }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }
    
    public static void main(String[] args) {
        Checkers2 checkers = new Checkers2();        
        checkers.setLocationRelativeTo(null);
        checkers.setVisible(true);
    }
    
}
