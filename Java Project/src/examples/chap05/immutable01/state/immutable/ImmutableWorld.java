/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap05.immutable01.state.immutable;

import examples.chap05.immutable01.state.Direction;
import examples.chap05.immutable01.state.ElementFactory;
import examples.chap05.immutable01.state.Space;
import examples.chap05.immutable01.state.StaticElement;
import examples.chap05.immutable01.state.Wall;
import examples.chap05.immutable01.state.World;
import examples.chap05.immutable01.state.WorldIterator;

public class ImmutableWorld extends World {
    
    public ImmutableWorld(World world) {
        this.width = world.getWidth();
        this.height = world.getHeight();
        this.elements = world.getElements();
    }
    
    public void init(int[][] level) {
        throw new IllegalAccessError();
    }    

    public void set(int x, int y, StaticElement e) {
        throw new IllegalAccessError();
    }

    public StaticElement get(int x, int y, Direction d) {
        StaticElement se = super.get(x, y, d);
        if (se instanceof Wall) {
            return new ImmutableWall((Wall)se);
        }
        if (se instanceof Space) {
            return new ImmutableSpace((Space)se);
        }
        throw new RuntimeException("Invalid element type");
    }

    public WorldIterator iterator() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    public ElementFactory getFactory() {
        throw new IllegalAccessError();
    }
    
    public void setFactory(ElementFactory factory) {
        throw new IllegalAccessError();
    }
 
    private Object writeReplace() {
        throw new IllegalAccessError();
    }

}
