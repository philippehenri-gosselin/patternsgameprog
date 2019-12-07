/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.collisions;

public class AABB {

    public int x0;

    public int y0;

    public int x1;

    public int y1;

    public AABB(int x0, int y0, int x1, int y1) {
        this.x0 = Math.min(x0,x1);
        this.y0 = Math.min(y0, y1);
        this.x1 = Math.max(x0,x1);
        this.y1 = Math.max(y0, y1);
    }
    
    public AABB(AABB aabb1,AABB aabb2) {
        x0 = Math.min(aabb1.x0, aabb2.x0);
        y0 = Math.min(aabb1.y0, aabb2.y0);
        x1 = Math.max(aabb1.x1, aabb2.x1);
        y1 = Math.max(aabb1.y1, aabb2.y1);
    }
    
    public int width() {
        return x1 - x0;
    }

    public int height() {
        return y1 - y0;
    }
    
    public long volume() {
        return (x1-x0)*(y1-y0);
    }
    
    public long volumeUnion(AABB aabb) {
        return (Math.max(x1, aabb.x1)-Math.min(x0, aabb.x0))
             * (Math.max(y1, aabb.y1)-Math.min(y0, aabb.y0));
    }
    
    public boolean contains(AABB aabb) {
        return x0 <= aabb.x0 
            && y0 <= aabb.y0
            && x1 >= aabb.x1
            && y1 >= aabb.y1;
    }

    public boolean collides(AABB aabb) {
        return x1 >= aabb.x0
            && y1 >= aabb.y0
            && x0 <= aabb.x1
            && y0 <= aabb.y1;
    }

    public void union(AABB aabb) {
        x0 = Math.min(x0, aabb.x0);
        y0 = Math.min(y0, aabb.y0);
        x1 = Math.max(x1, aabb.x1);
        y1 = Math.max(y1, aabb.y1);
    }
    
    public void union(AABB aabb1,AABB aabb2) {
        x0 = Math.min(aabb1.x0, aabb2.x0);
        y0 = Math.min(aabb1.y0, aabb2.y0);
        x1 = Math.max(aabb1.x1, aabb2.x1);
        y1 = Math.max(aabb1.y1, aabb2.y1);
    }
    
    public boolean equals(Object other) {
        if (!(other instanceof AABB))
            return false;
        AABB aabb = (AABB)other;
        return x0 == aabb.x0
            && y0 == aabb.y0
            && x1 == aabb.x1
            && y1 == aabb.y1;
    }
    
    public String toString() {
        return String.format("(%d,%d,%d,%d)",x0,y0,x1,y1);
    }
}
