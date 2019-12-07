/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.draw02.gui;

import java.io.IOException;

public interface Layer {

    public int getTileWidth();

    public int getTileHeight();

    public int getTextureWidth();

    public int getTextureHeight();

    public void setTileSize(int width, int height);

    public void setTexture(String fileName);

    public void setSpriteCount(int count);

    public void setSpriteTexture(int index, int tileX, int tileY);

    public void setSpriteLocation(int index, int screenX, int screenY);
}
