/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package pacman.state.mutable;

import pacman.state.Element;

public abstract class MutableElement implements Element {

    @Override
    public Element clone() {
        try {
            return (Element)super.clone();
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException();
        }    
    }

}
