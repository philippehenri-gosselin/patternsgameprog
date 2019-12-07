/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.features02.render;

import examples.chap04.features02.state.Element;

public interface TileSet {

    public int getTileWidth();

    public int getTileHeight();

    public String getImageFile();

    public Tile getTile(Element e);
}
