/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.collisions;

import java.util.ArrayList;
import java.util.List;

public class ExhaustiveCollider implements Collider {

    protected final List<AABB> boxes;

    public ExhaustiveCollider(List<AABB> boxes) {
        this.boxes = boxes;
    }
    
    public List<AABB> collides(AABB aabb) {
        ArrayList<AABB> result = new ArrayList();
        for (AABB box: boxes) {
            if(box.collides(aabb)) {
                result.add(box);
            }
        }
        return result;
    }
}
