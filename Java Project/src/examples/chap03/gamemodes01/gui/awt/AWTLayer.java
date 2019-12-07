/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap03.gamemodes01.gui.awt;

import examples.chap03.gamemodes01.gui.Layer;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class AWTLayer implements Layer {

    private int tileWidth;

    private int tileHeight;

    private BufferedImage texture;

    private int textureWidth;

    private int textureHeight;

    private int[][] sprites;

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public int getTextureWidth() {
        return textureWidth;
    }

    public int getTextureHeight() {
        return textureHeight;
    }

    public void setTileSize(int width, int height) {
        this.tileWidth = width;
        this.tileHeight = height;
    }

    public void setTexture(String fileName) {
        if (tileWidth == 0 || tileHeight == 0) {
            throw new RuntimeException("Tile size is not defined");
        }
        try {
            texture = ImageIO.read(this.getClass().getClassLoader().getResource(fileName));
        } catch (IOException ex) {
            throw new RuntimeException("Error when reading "+fileName);
        }
        textureWidth = texture.getWidth() / tileWidth;
        textureHeight = texture.getHeight() / tileHeight;
    }

    public void setSpriteCount(int count) {
        sprites = new int[count][4];
    }

    public void setSpriteTexture(int index, int tileX, int tileY) {
        if (sprites == null) {
            throw new RuntimeException("Sprites are not defined");
        }
        if (index < 0 || index >= sprites.length) {
            throw new IllegalArgumentException("Invalid sprite index "+index);
        }
        if (tileX < 0 || tileX >= textureWidth || tileY < 0 || tileY >= textureHeight) {
            throw new IllegalArgumentException("Invalid tile coordinates "+tileX+","+tileY);
        }
        sprites[index][0] = tileX;
        sprites[index][1] = tileY;
    }

    public void setSpriteLocation(int index, int screenX, int screenY) {
        if (sprites == null || index < 0 || index >= sprites.length)
            return;
        sprites[index][2] = screenX;
        sprites[index][3] = screenY;
    }

    public void draw(Graphics graphics) {
        if (texture == null) {
            throw new RuntimeException("Texture not loaded");
        }
        if (sprites == null) {
            throw new RuntimeException("Sprites are not defined");
        }
        if (tileWidth == 0 || tileHeight == 0) {
            throw new RuntimeException("Tile size is not defined");
        }
        for (int i = 0; i < sprites.length; i++) {
            int tileX = sprites[i][0];
            int tileY = sprites[i][1];
            int screenX = sprites[i][2];
            int screenY = sprites[i][3];
            graphics.drawImage(texture, screenX, screenY, screenX + tileWidth, screenY + tileHeight, tileX * tileWidth, tileY * tileHeight, tileX * tileWidth + tileWidth, tileY * tileHeight + tileHeight, null);
        }
    }
}
