/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.features02.state;
 
import java.util.Arrays;

public class World implements Iterable<StaticElement> {

    private int width;

    private int height;

    private StaticElement[][] elements;

    private ElementFactory factory;
    
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
    
    public void init(int[][] level) {
        for (int y=0;y<height;y++) {
            for (int x=0;x<width;x++) {
                Element element = factory.create(level[y][x]);
                elements[x][y] = (StaticElement)element;
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
                while (x < 0) x += width;
                while (x >= width) x -= width;
                while (y < 0) y += height;
                while (y >= height) y -= height;
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

    public ElementFactory getFactory() {
        return factory;
    }
    
    public void setFactory(ElementFactory factory) {
        this.factory = factory;
    }
    
    public StaticElement[][] getElements() {
        return elements;
    }
    
    @Override
    public World clone() {
        World other = new World(width,height);
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                other.elements[i][j] = (StaticElement)elements[i][j].clone();
            }
        }
        return other;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final World other = (World) obj;
        if (this.width != other.width) {
            return false;
        }
        if (this.height != other.height) {
            return false;
        }
        if (!Arrays.deepEquals(this.elements, other.elements)) {
            return false;
        }
        return true;
    }
    
}
