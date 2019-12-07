/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.render;

public interface Tile {

    public int getX(long time);

    public int getY(long time);
}
