/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.network02.state;

public interface World extends Iterable<StaticElement> {

    public int getWidth();

    public int getHeight();

    public StaticElement get(int x, int y, Direction d);

    public void set(int x, int y, StaticElement e);

    public ElementFactory getFactory();
    
    public void setFactory(ElementFactory factory);
    
    public void init(int[][] level);

    public WorldIterator iterator();

    public boolean equals(Object obj);

    public World clone();
}
