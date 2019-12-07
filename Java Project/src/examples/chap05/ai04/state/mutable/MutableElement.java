/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap05.ai04.state.mutable;

import examples.chap05.ai04.state.Element;

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
