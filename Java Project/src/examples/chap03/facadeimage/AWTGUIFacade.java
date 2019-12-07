/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap03.facadeimage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class AWTGUIFacade implements GUIFacade {

    private AWTWindow window;

    private Graphics graphics;

    public void createWindow(String title) {
        window = new AWTWindow();
        window.init(title);
        window.createCanvas();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public boolean isClosingRequested() {
        return window.isClosingRequested();
    }

    public void dispose() {
        window.dispose();
    }

    public boolean beginPaint() {
        if (graphics != null) {
            graphics.dispose();
        }
        graphics = window.createGraphics();
        if (graphics == null) {
            return false;
        }
        return true;
    }

    public void endPaint() {
        if (graphics == null) {
            return;
        }
        graphics.dispose();
        graphics = null;
        window.switchBuffers();
    }

    public void clearBackground() {
        if (graphics == null) {
            return;
        }
        graphics.setColor(Color.black);
        graphics.fillRect(0, 0, window.getCanvasWidth(), window.getCanvasHeight());
    }
    
    public Image createImage(String fileName) {
        AWTImage image = new AWTImage();
        image.loadImage(fileName);
        return image;
    }

    public void drawImage(Image image,int x,int y) {
        if (graphics == null) {
            return;
        }
        if (image == null)
            throw new IllegalArgumentException("Pas de image");
        if (!(image instanceof AWTImage))
            throw new IllegalArgumentException("type d'image invalide");
        AWTImage awtImage = (AWTImage) image;
        awtImage.draw(graphics,x,y);
    }
    

}
