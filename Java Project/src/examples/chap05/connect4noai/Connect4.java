/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap05.connect4noai;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Connect4 extends JFrame implements KeyListener, MouseListener {

    private Board board;

    private BoardComponent boardComponent;
    
    private JLabel statusBar;    
    
    private Queue<Command> commands = Collections.asLifoQueue(new ArrayDeque());
    
    public Connect4() 
    {
        board = new Board();
        boardComponent = new BoardComponent(board);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setTitle("Puissance 4");
        setLayout(new BorderLayout());
        getContentPane().add(boardComponent,BorderLayout.CENTER); 
        statusBar = new JLabel("Current player");
        getContentPane().add(statusBar,BorderLayout.SOUTH);
        pack();        
        redraw();
        
        addMouseListener(this);
        addKeyListener(this);
        
        requestFocusInWindow();
    }     
    
    public void redraw() {
        String msg = (board.getCurrentPlayer()==1)?"Rouge":"Jaune";
        if (board.isWinner(1)) {
            msg += ", le joueur rouge gagne";
        }
        if (board.isWinner(-1)) {
            msg += ", le joueur jaune gagne";
        }
        statusBar.setText("Current player: "+msg);
        repaint();
    }    
    
    public void play(Command command) {
        if (board.getWinner() == 0 && command != null) {
            commands.add(command);
            command.execute(board);
            
            redraw();
        }
    }
    
    public void undo() {
        if (!commands.isEmpty()) {
            commands.poll().rollback(board);
            redraw();
        }
    }
    
    public void reset() {
        board.reset(1);
        commands.clear();
        redraw();
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        int i = (e.getX() * 7) / getWidth();
        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                play(new Command(i));
                break;
            case MouseEvent.BUTTON3:
                undo();
                break;
            default:
                break;
        }
        redraw();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_BACK_SPACE:
                undo();
                break;
            case KeyEvent.VK_ESCAPE:
                reset();
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    public static void main(String[] args) {
        Connect4 connect4 = new Connect4();        
        connect4.setLocationRelativeTo(null);
        connect4.setVisible(true);
    }      
}
