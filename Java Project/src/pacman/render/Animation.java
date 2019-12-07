/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.render;

import pacman.gui.Layer;

public class Animation {

    protected Tile tile;

    public void render(LayerElement element, long begin, long time, long end) {
        Layer layer = element.getLayer();
        int index = element.getIndex();
        layer.setSpriteTexture(index, tile.getX(time),tile.getY(time));
    }

    
    public Tile getTile() {
        return tile;
    }
    
    public void setTile(Tile tile) {
        this.tile = tile;
    }

}
