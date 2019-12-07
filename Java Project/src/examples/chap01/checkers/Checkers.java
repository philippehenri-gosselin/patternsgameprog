/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap01.checkers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Checkers extends JFrame implements MouseListener {
    private int board[][];
    private int currentPlayer;
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

                    int p = board[i][j];
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
    
    public Checkers() {
        currentPlayer = 0;
        board = new int[8][8];
        for (int j=0;j<8;j++) {
            for (int i=0;i<8;i++) {
                board[i][j] = -1;
            }
        }
        board[0][0] = board[2][0] = board[4][0] = board[6][0] = 0;
        board[1][1] = board[3][1] = board[5][1] = board[7][1] = 0;
        board[0][6] = board[2][6] = board[4][6] = board[6][6] = 1;
        board[1][7] = board[3][7] = board[5][7] = board[7][7] = 1;
        
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
        String msg = (currentPlayer==0)?"Black":"White";
        statusBar.setText("Current player: "+msg);
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) {
        int i = e.getX() / 80;
        int j = e.getY() / 80;
        if (board[i][j] == currentPlayer) {
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
        if (board[i][j] < 0 && ((i+j)%2) == 0) {
            boolean doMove = false;
            int i0 = selectedPiece[0];
            int j0 = selectedPiece[1];
            if ( (i==i0+1 && j==j0+1)
              || (i==i0+1 && j==j0-1)
              || (i==i0-1 && j==j0+1)
              || (i==i0-1 && j==j0-1)) {
                doMove = true;
            }
            else if (i==i0+2 && j==j0+2 && board[i0+1][j0+1] == 1-board[i0][j0]) {
                board[i0+1][j0+1] = -1;
                doMove = true;
            }            
            else if (i==i0-2 && j==j0+2 && board[i0-1][j0+1] == 1-board[i0][j0]) {
                board[i0-1][j0+1] = -1;
                doMove = true;
            }            
            else if (i==i0+2 && j==j0-2 && board[i0+1][j0-1] == 1-board[i0][j0]) {
                board[i0+1][j0-1] = -1;
                doMove = true;
            }            
            else if (i==i0-2 && j==j0-2 && board[i0-1][j0-1] == 1-board[i0][j0]) {
                board[i0-1][j0-1] = -1;
                doMove = true;
            }            
            if (doMove) {
                board[i][j] = board[i0][j0];
                board[i0][j0] = -1;
                currentPlayer = 1-currentPlayer;
                redraw();
            }
        }
        selectedPiece = null;
    }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }
    
    public static void main(String[] args) {
        Checkers checkers = new Checkers();        
        checkers.setLocationRelativeTo(null);
        checkers.setVisible(true);
    }
    
}
