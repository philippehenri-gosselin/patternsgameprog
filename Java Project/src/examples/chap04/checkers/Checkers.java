/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.checkers;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Checkers extends JFrame implements MouseListener {

    private Rules rules;

    private int[] selectedPiece;

    private JLabel statusBar;

    public Checkers() 
    {
        Board board = new Board();
        rules = new Rules(board);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Checkers");
        setLayout(new BorderLayout());
        getContentPane().add(new BoardComponent(board),BorderLayout.CENTER); 
        statusBar = new JLabel("Current player");
        getContentPane().add(statusBar,BorderLayout.SOUTH);
        pack();        
        redraw();
        
        addMouseListener(this);
    }    

    public void redraw() {
        String msg = (rules.getBoard().getCurrentPlayer()==0)?"Black":"White";
        statusBar.setText("Current player: "+msg);
        repaint();
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int i = e.getX() / 80;
        int j = e.getY() / 80;
        Board board = rules.getBoard();
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
        rules.setCommand(new MoveCommand(selectedPiece[0], selectedPiece[1], i, j));
        rules.update();
        redraw();
        selectedPiece = null;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public static void main(String[] args) {
        Checkers checkers = new Checkers();        
        checkers.setLocationRelativeTo(null);
        checkers.setVisible(true);
    }    
}
