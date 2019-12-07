/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.ai03.render;

import java.util.ArrayList;
import java.util.List;

public class AnimatedTile implements Tile {

    private List<Tile> tiles = new ArrayList();

    private int rate;
    
    public AnimatedTile(int rate) {
        this.rate = rate;
    }
    
    private final int getIndex(long time) {
        time *= rate;
        time /= 1000000000;
        return (int)(time % tiles.size());
    }

    public int getX(long time) {
        return tiles.get(getIndex(time)).getX(time);
    }

    public int getY(long time) {
        return tiles.get(getIndex(time)).getY(time);
    }

    public void addTile(Tile tile) {
        tiles.add(tile);
    }
}
