/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.ai03.ai.map;

import examples.chap05.ai03.state.Direction;

public class Point implements Comparable<Point> {

    public final int x;

    public final int y;

    public final int weight;

    public Point(int x, int y, int weight) {
        this.x = x;
        this.y = y;
        this.weight = weight;
    }
    
    public int compareTo(Point point) {
        return this.weight - point.weight;
    }
    
    public Point transform(Direction direction,int weight) {
        switch(direction) {
            case NONE: return this;
            case EAST: return new Point(x+1,y,weight);
            case WEST: return new Point(x-1,y,weight);
            case NORTH: return new Point(x,y-1,weight);
            case SOUTH: return new Point(x,y+1,weight);
        }
        throw new IllegalArgumentException("Invalid direction");
    }
}
