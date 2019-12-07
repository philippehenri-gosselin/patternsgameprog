/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap06.collisions;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class Display extends JFrame implements KeyListener {

    private AABB cursor;
    
    private ArrayList<AABB> boxes;
    
    private Collider collider;
    
    public Display(ArrayList<AABB> boxes,Collider collider) 
    {
        this.cursor = new AABB(370,280,430,320);
        this.boxes = boxes;
        this.collider = collider;
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setTitle("Puissance 4");
        setLayout(new BorderLayout());
        getContentPane().add(new InnerComponent(),BorderLayout.CENTER); 
        pack();        
        redraw();
        
        addKeyListener(this);
        
        requestFocusInWindow();
    }         
    
    public void redraw() {
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                cursor = new AABB(cursor.x0+1,cursor.y0,cursor.x1+1,cursor.y1);
                break;
            case KeyEvent.VK_LEFT:
                cursor = new AABB(cursor.x0-1,cursor.y0,cursor.x1-1,cursor.y1);
                break;
            case KeyEvent.VK_DOWN:
                cursor = new AABB(cursor.x0,cursor.y0+1,cursor.x1,cursor.y1+1);
                break;
            case KeyEvent.VK_UP:
                cursor = new AABB(cursor.x0,cursor.y0-1,cursor.x1,cursor.y1-1);
                break;
            case KeyEvent.VK_ESCAPE:
                dispose();
                break;
            default:
                break;
        }
        redraw();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }    
    
        
    public class InnerComponent extends JComponent {
    
        public InnerComponent() {
            setMinimumSize(new Dimension(800,600));
            setPreferredSize(new Dimension(800,600));
            setMaximumSize(new Dimension(800,600));
        }

        @Override
        public void paint(Graphics g) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.WHITE);
            for (AABB aabb : boxes) {
                g.drawRect(aabb.x0, aabb.y0, aabb.width(), aabb.height());
            }
            g.setColor(Color.RED);
            for (AABB aabb : collider.collides(cursor)) {
                g.drawRect(aabb.x0, aabb.y0, aabb.width(), aabb.height());
            }
            g.setColor(Color.GREEN);
            g.drawRect(cursor.x0, cursor.y0, cursor.width(), cursor.height());
        }
    }
    
    public static void main(String[] args) {
        ArrayList<AABB> boxes = new ArrayList();
        Random random = new Random(1);
        for (int i=0;i<50;i++) {
            int x0 = 50+random.nextInt(600);
            int y0 = 50+random.nextInt(400);
            int x1 = x0+10+random.nextInt(100);
            int y1 = y0+10+random.nextInt(100);
            boxes.add(new AABB(x0,y0,x1,y1));
        }
            
        Collider collider = new ExhaustiveCollider(boxes);
        //Collider collider = new TreeCollider(boxes);
        
        Display connect4 = new Display(boxes,collider);        
        connect4.setLocationRelativeTo(null);
        connect4.setVisible(true);
    }       
}
