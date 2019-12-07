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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class E07Level extends Frame {
    
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
        pack();
    }
    
    public void loadTexture() throws IOException {
        texture = ImageIO.read(this.getClass().getClassLoader().getResource("grid_tiles.png"));
        textureWidth = texture.getWidth() / tileWidth;
        textureHeight = texture.getHeight() / tileHeight;
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
                    int tileIndex = level[j][i];
                    if (tileIndex < 0)
                        tileIndex = 0;
                    int tileX = (tileIndex-1) % textureWidth;
                    int tileY = (tileIndex-1) / textureHeight;
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
            
            int tileX = 0;
            int tileY = 2;
            int screenX = 0;
            int screenY = 4*tileHeight;
            g.drawImage(texture, 
                screenX, screenY, screenX + tileWidth, screenY + tileHeight,
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
    
    public static void main(String args[]) throws IOException {
        E07Level window = new E07Level();
        window.init();
        window.loadTexture();
        window.createCanvas();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.run();
    }    
}
