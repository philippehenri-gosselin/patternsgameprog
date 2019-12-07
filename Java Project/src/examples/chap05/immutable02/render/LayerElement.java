/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.immutable02.render;

import examples.chap05.immutable02.gui.Layer;

public class LayerElement {

    private final Layer layer;

    private final int index;
    
    public LayerElement(Layer layer,int index) {
        this.layer = layer;
        this.index = index;
    }
    
    public Layer getLayer() {
        return layer;
    }
    
    public int getIndex() {
        return index;
    }
    
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (!(other instanceof LayerElement))
            return false;
        LayerElement element = (LayerElement)other;
        if (this.layer != element.layer)
            return false;
        if  (this.index != element.index)
            return false;
        return true;
    }
    
    public int hashCode() {
        return layer.hashCode() + index;
    }
}
