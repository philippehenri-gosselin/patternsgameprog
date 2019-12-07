/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap03.lwjgl07;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.IntBuffer;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;

public class Texture {

    private int id;

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public int getId() {
        return id;
    }

    public void load(String fileName) throws IOException 
    {
        BufferedImage image = ImageIO.read(this.getClass().getClassLoader().getResource(fileName));
        int width = image.getWidth();
        int height = image.getHeight();
        int[] bgrPixels = new int[width * height];
        image.getRGB(0,0,width,height,bgrPixels,0,width);
        int[] rgbPixels = new int[width * height];
        for (int i=0;i<bgrPixels.length;i++) {
            int a = (bgrPixels[i] & 0xff000000) >> 24;
            int r = (bgrPixels[i] & 0x00ff0000) >> 16;
            int g = (bgrPixels[i] & 0x0000ff00) >> 8;
            int b =  bgrPixels[i] & 0x000000ff;
            rgbPixels[i] = a << 24 | b << 16 | g << 8 | r;
        }

        IntBuffer pixelsBuffer = BufferUtils.createIntBuffer(rgbPixels.length);
        pixelsBuffer.put(rgbPixels);
        pixelsBuffer.flip();
        
        id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);        
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0,
                GL_RGBA, GL_UNSIGNED_BYTE, pixelsBuffer);
    }

    public void dispose() {
        glDeleteTextures(id);
    }
}
