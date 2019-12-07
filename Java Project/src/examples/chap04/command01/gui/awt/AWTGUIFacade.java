/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.command01.gui.awt;

import examples.chap04.command01.gui.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
 
public class AWTGUIFacade implements GUIFacade {

    private AWTWindow window;

    private Graphics graphics;
    
    private AWTFonts fonts;
    
    public AWTGUIFacade() {
        fonts = new AWTFonts();
    }

    public void createWindow(int width, int height, String title) {
        if (window == null) {
            window = new AWTWindow();
        }
        window.init(title);
        window.createCanvas(width, height);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public boolean isClosingRequested() {
        return window.isClosingRequested();
    }
    
    
    public void setClosingRequested(boolean closingRequested) {
        window.setClosingRequested(closingRequested);
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

    @Override
    public Layer createLayer() {
        return new AWTLayer();
    }

    @Override
    public void drawLayer(Layer layer) {
        if (graphics == null) {
            return;
        }
        if (layer == null)
            throw new IllegalArgumentException("No layer");
        if (!(layer instanceof AWTLayer))
            throw new IllegalArgumentException("Invalid layer type");
        AWTLayer awtLayer = (AWTLayer) layer;
        awtLayer.draw(graphics);
        //saveLayer(awtLayer);
    }
    
    public void saveLayer(AWTLayer layer) {
        BufferedImage image = new BufferedImage(window.getCanvasWidth(),window.getCanvasHeight(),BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = image.getGraphics();
        layer.draw(g);
        g.dispose();
        try {
            ImageIO.write(image, "png", new File(layer.toString()+".png"));
        } catch (IOException ex) {
            Logger.getLogger(AWTGUIFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Keyboard getKeyboard() {
        if (window == null)
            throw new RuntimeException("Il faut d'abord créer une fenêtre");
        return window.getKeyboard();
    }

    public Mouse getMouse() {
        if (window == null)
            throw new RuntimeException("Il faut d'abord créer une fenêtre");
        return window.getMouse();
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
    
    public void setColor(Color color) {
        if (graphics == null)
            return;
        graphics.setColor(color);
    }
    
    public void setTextSize(int size) {
        if (graphics == null)
            return;
        graphics.setFont(fonts.getFont(graphics,size));
    }
    
    @Override
    public Dimension getTextMetrics(String text) {
        FontMetrics fm = graphics.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        return new Dimension(textWidth,textHeight);
    }    
    
    public void drawText(String text, int x, int y, int width, int height) {
        if (graphics == null)
            return;
        FontMetrics fm = graphics.getFontMetrics();
        graphics.clipRect(x, y, width, height);
        graphics.drawString(text, x, y+fm.getAscent());
        graphics.setClip(null);
    }


}
