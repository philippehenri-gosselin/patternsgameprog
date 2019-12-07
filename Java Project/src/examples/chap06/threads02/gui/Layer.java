/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.threads02.gui;

import java.awt.Point;

public interface Layer {

    public int getTileWidth();

    public int getTileHeight();

    public int getTextureWidth();

    public int getTextureHeight();

    public Point getSpriteLocation(int index);
    
    public void setTileSize(int width, int height);

    public void setTexture(String fileName);

    public void setSpriteCount(int count);

    public void setSpriteTexture(int index, int tileX, int tileY);

    public void setSpriteLocation(int index, int screenX, int screenY);
    
}
