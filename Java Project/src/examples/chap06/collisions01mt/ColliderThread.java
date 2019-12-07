/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap06.collisions01mt;

import examples.chap06.collisions.AABB;
import examples.chap06.collisions.ExhaustiveCollider;
import java.util.List;
import java.util.concurrent.Callable;

class ColliderThread extends ExhaustiveCollider implements Callable<List<AABB>> {

    private final AABB aabb;

    public ColliderThread(List<AABB> boxes,AABB aabb) {
        super(boxes);
        this.aabb = aabb;
    }

    public List<AABB> call() throws Exception {
        return collides(aabb);
    }

}
