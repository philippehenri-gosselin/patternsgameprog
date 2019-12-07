/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.network03.render;

import examples.chap06.network03.gui.Layer;
import java.awt.Point;

public class MobileAnimation extends Animation {

    private Point start;
    
    private Point dest;    

    public void render(LayerElement element, long begin, long time, long end) {
        super.render(element,begin,time,end);

        Layer layer = element.getLayer();
        int index = element.getIndex();
        if (time < end) {
            double step = time - begin;
            step /= end - begin;
            int dx = (int) ((dest.x - start.x) * step);
            int dy = (int) ((dest.y - start.y) * step);

            layer.setSpriteLocation(index, start.x+dx, start.y+dy);
        }
        else {
            layer.setSpriteLocation(index, dest.x, dest.y);
        }
    }
    
    public Point getStart() {
        return start;
    }
    
    public Point getDest() {
        return dest;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public void setDest(Point dest) {
       this.dest = dest;
    }


}
