/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.collisions;

import java.util.List;

public interface Collider {

    public List<AABB> collides(AABB aabb);
}
