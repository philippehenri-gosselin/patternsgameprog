/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap02.pacman;

public class World implements Iterable<StaticElement> {

    private int width;

    private int height;

    private StaticElement[][] elements;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        elements = new StaticElement[width][height];
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                elements[i][j] = new Space(SpaceTypeId.EMPTY);
            }
        }
    }

    public void set(int x, int y, StaticElement e) {
        if (x < 0 || x >= width || y < 0 || y >= height)
            throw new IllegalArgumentException("Coordonnées " + x + "," + y + " invalides");
        elements[x][y] = e;
    }

    public StaticElement get(int x, int y, Direction d) {
        switch(d) {
            case NONE:
                if (x < 0 || x >= width || y < 0 || y >= height)
                    throw new IllegalArgumentException("Coordonnées " + x + "," + y + " invalides");
                return elements[x][y];
            case NORTH:
                return get(x, y - 1, Direction.NONE);
            case SOUTH:
                return get(x, y + 1, Direction.NONE);
            case EAST:
                return get(x + 1, y, Direction.NONE);
            case WEST:
                return get(x - 1, y, Direction.NONE);
        }
        throw new RuntimeException("Invalid Direction");
    }

    public WorldIterator iterator() {
        return new WorldIterator(this);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public StaticElement[][] getElements() {
        return elements;
    }
}
