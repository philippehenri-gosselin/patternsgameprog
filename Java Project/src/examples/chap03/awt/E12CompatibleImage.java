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
import java.awt.Transparency;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class E12CompatibleImage extends Frame {
    
    private int canvasWidth = 800;
    private int canvasHeight = 600;
    private Canvas canvas;
    private boolean running = true;
    
    private BufferedImage texture;
    private int tileWidth = 24;
    private int tileHeight = 24;
    private int textureWidth;
    private int textureHeight;
    
    static final int levelWidth = 9;
    static final int levelHeight = 6;
    static final int[][] level = new int[][] {
        { 15,11,11,11,11,11,11,11,16 },
        { 12,5,3,3,3,3,3,3,12 },
        { 12,3,15,11,11,11,16,3,12 },
        { 14,3,13,11,11,11,14,3,13 },
        { 3,3,3,3,3,3,3,3,3 },
        { 11,11,11,11,11,11,11,11,11 }
    };    
    
    private static class Keyboard implements KeyListener {

        private boolean[] keys;

        public Keyboard() {
            keys = new boolean[0x10000];
        }

        public boolean isKeyPressed(int keyCode) {
            if (keyCode >= keys.length)
                return false;
            return keys[keyCode];
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() < keys.length) {
                keys[e.getKeyCode()] = true;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() < keys.length) {
                keys[e.getKeyCode()] = false;
            }
        }

    }

    
    private Keyboard keyboard;
    private int pacmanX;
    private int pacmanY;
    
    private static class Mouse implements MouseListener, MouseMotionListener {

        private static boolean[] buttons;
        private static int x;
        private static int y;
        
        public Mouse() {
            buttons = new boolean[4];
        }
        
        public boolean isButtonPressed(int button) {
            if(button >= buttons.length)
                return false;
            return buttons[button];
        }
        
        public int getX() {
            return x;
        }
        
        public int getY() {
            return y;
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() <= 3) {
                buttons[e.getButton()] = true;
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.getButton() <= 3) {
                buttons[e.getButton()] = false;
            }        
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            x = e.getX();
            y = e.getY();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            x = e.getX();
            y = e.getY();
        }
        
    }
    
    private Mouse mouse;
    private int selectedTileX;
    private int selectedTileY;
    
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
        canvasWidth = levelWidth * tileWidth;
        canvasHeight = levelHeight * tileHeight;
        
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(canvasWidth,canvasHeight));
        canvas.setMinimumSize(new Dimension(canvasWidth,canvasHeight));
        canvas.setMaximumSize(new Dimension(canvasWidth,canvasHeight));
        add(canvas);

        keyboard = new Keyboard();
        canvas.addKeyListener(keyboard);
        mouse = new Mouse();
        canvas.addMouseListener(mouse);
        canvas.addMouseMotionListener(mouse);
        
        pack();
    }
    
    public void loadTexture() throws IOException {
        texture = ImageIO.read(this.getClass().getClassLoader().getResource("grid_tiles.png"));
        textureWidth = texture.getWidth() / tileWidth;
        textureHeight = texture.getHeight() / tileHeight;
        
        Graphics g = null;
        try {
            BufferedImage compatibleTexture = getGraphicsConfiguration().createCompatibleImage(texture.getWidth(), texture.getHeight(), Transparency.TRANSLUCENT);
            g = compatibleTexture.createGraphics();
            g.drawImage(texture, 0, 0, null);
            texture = compatibleTexture;
        }
        finally {
            if (g != null) {
                g.dispose();
            }
        }        
    }

    public void handleInputs() {
        canvas.requestFocus();        
        
        if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)) {
            pacmanX ++;
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)) {
            pacmanX --;
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)) {
            pacmanY ++;
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_UP)) {
            pacmanY --;
        }
        
        selectedTileX = mouse.getX() / tileWidth;
        selectedTileY = mouse.getY() / tileHeight;
        
        if (selectedTileX >= 0 && selectedTileX < levelWidth
         && selectedTileY >= 0 && selectedTileY < levelHeight) {
            if (mouse.isButtonPressed(MouseEvent.BUTTON1)) {
                level[selectedTileY][selectedTileX] = 2;
            }
            if (mouse.isButtonPressed(MouseEvent.BUTTON3)) {
                level[selectedTileY][selectedTileX] = 5;
            }
        }
    }
    
    private long lastUpdate;
    public void update() {
        long now = System.nanoTime();
        if ( (now - lastUpdate) < 1000000000/4)
            return;
        lastUpdate = now;
        for (int j=0;j<levelHeight;j++) {
            for (int i=0;i<levelWidth;i++) {    
                int id = level[j][i];
                if (id == 5) level[j][i] = 4;
                else if (id == 4) level[j][i] = 5;
            }
        }
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

            for (int j=0;j<levelHeight;j++) {
                for (int i=0;i<levelWidth;i++) {
                    int tileIndex = level[j][i] - 1;
                    if (tileIndex < 0)
                        tileIndex = 0;
                    int tileX = tileIndex % textureWidth;
                    int tileY = tileIndex / textureHeight;
                    if (tileY >= textureHeight) {
                        tileX = 0;
                        tileY = 0;
                    }                
                    g.drawImage(texture, 
                        i * tileWidth, j * tileHeight, i * tileWidth + tileWidth, j * tileHeight + tileHeight,
                        tileX * tileWidth, tileY * tileHeight, tileX * tileWidth + tileWidth, tileY * tileHeight + tileHeight,
                        null
                    );
                }
            }                 

            // Rectangle de sélection
            int tileX = 5;
            int tileY = 0;
            g.drawImage(texture, 
                selectedTileX * tileWidth, selectedTileY * tileHeight, selectedTileX * tileWidth + tileWidth, selectedTileY * tileHeight + tileHeight,
                tileX * tileWidth, tileY * tileHeight, tileX * tileWidth + tileWidth, tileY * tileHeight + tileHeight,
                null
            );            
            
            // Pacman
            tileX = 0;
            tileY = 2;
            g.drawImage(texture, 
                pacmanX, pacmanY, pacmanX + tileWidth, pacmanY + tileHeight,
                tileX * tileWidth, tileY * tileHeight, tileX * tileWidth + tileWidth, tileY * tileHeight + tileHeight,
                null
            );            
            
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
            
            handleInputs();
            update();
            render();
            
            long elapsed = System.nanoTime() - lastTime;
            long miliSleep = (nanoPerFrame - elapsed) / 1000000;
            if (miliSleep > 0) {
                try {
                    Thread.sleep (miliSleep);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }            
        }
        dispose();
    }
    
    public void bench() 
    {        
        int count = 0;
        long begin = System.nanoTime();

        while (running) {            
            handleInputs();
            update();
            render();
            count ++;
        }
        
        long end = System.nanoTime();
        double fps = count / ((end - begin) / 1000000000.0);
        System.out.println("Frames per second: "+fps);
        
        dispose();
    }    
    
    public static void main(String args[]) throws IOException {
        E12CompatibleImage window = new E12CompatibleImage();
        window.init();
        window.loadTexture();
        window.createCanvas();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.bench();                
    }    
}
