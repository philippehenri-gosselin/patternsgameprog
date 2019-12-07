/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.draw01.state;

public class State {

    private Characters chars;

    private World world;

    public State() {
    }

    public Characters getChars() {
        return chars;
    }

    public World getWorld() {
        return world;
    }

    public void setChars(Characters chars) {
        this.chars = chars;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
