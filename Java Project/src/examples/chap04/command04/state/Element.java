/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.command04.state;

public abstract class Element implements Cloneable {
    
    @Override
    public Element clone() {
        try {
            return (Element)super.clone();
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException();
        }
    }
    
}
