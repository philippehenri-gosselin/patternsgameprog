/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.immutable02.gui.awt;

import examples.chap05.immutable02.gui.Image;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class AWTImage implements Image {

    private BufferedImage image;
    
    public int getWidth() {
        if (image == null) {
            throw new RuntimeException("The image was not loaded");
        }
        return image.getWidth();
    }

    public int getHeight() {
        if (image == null) {
            throw new RuntimeException("The image was not loaded");
        }
        return image.getHeight();
    }
    
    public void loadImage(String fileName) {
        try {
            image = ImageIO.read(this.getClass().getClassLoader().getResource(fileName));
        } catch (IOException ex) {
            throw new RuntimeException("Error when reading "+fileName);
        }
    }
    
    public void draw(Graphics graphics,int x,int y) {
        graphics.drawImage(image, x, y, null);
    }
}
