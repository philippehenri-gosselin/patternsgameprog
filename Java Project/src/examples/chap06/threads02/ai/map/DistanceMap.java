/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.threads02.ai.map;

import examples.chap06.threads02.state.Direction;

public class DistanceMap {

    private final int width;

    private final int height;

    private final int[][] weights;
    
    public DistanceMap(int width,int height) {
        this.width = width;
        this.height = height;
        this.weights = new int[width][height];
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public int getWeight(int x, int y, Direction direction) {
        switch(direction) {
            case NONE:
                while (x < 0) x += width;
                while (x >= width) x -= width;
                while (y < 0) y += height;
                while (y >= height) y -= height;
                return weights[x][y];
            case NORTH:
                return getWeight(x, y - 1, Direction.NONE);
            case SOUTH:
                return getWeight(x, y + 1, Direction.NONE);
            case EAST:
                return getWeight(x + 1, y, Direction.NONE);
            case WEST:
                return getWeight(x - 1, y, Direction.NONE);
        }
        throw new RuntimeException("Invalid Direction");
    }
    
    public void setWeight(int x, int y, int weight) {
        while (x < 0) x += width;
        while (x >= width) x -= width;
        while (y < 0) y += height;
        while (y >= height) y -= height;
        weights[x][y] = weight;
    }
    
    public void init(int value) {
        for (int x=0;x<width;x++)
        for (int y=0;y<height;y++)
            weights[x][y] = value;
    }
}
