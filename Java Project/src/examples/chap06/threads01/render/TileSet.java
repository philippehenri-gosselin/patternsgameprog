/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.threads01.render;

import examples.chap06.threads01.state.Element;

public interface TileSet {

    public int getTileWidth();

    public int getTileHeight();

    public String getImageFile();

    public Tile getTile(Element e);
}
