/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.render;

public class StaticTile implements Tile {

    private int x;

    private int y;
    
    public StaticTile(int x,int y) {
        this.x = x;
        this.y = y;
    }

    public int getX(long time) {
        return x;
    }

    public int getY(long time) {
        return y;
    }
}
