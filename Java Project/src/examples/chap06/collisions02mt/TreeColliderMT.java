/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap06.collisions02mt;

import examples.chap06.collisions.AABB;
import examples.chap06.collisions.TreeCollider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class TreeColliderMT extends TreeCollider {

    public TreeColliderMT(List<AABB> boxes) {
        super(boxes);
    }

    public List<AABB> collides(AABB aabb) {
        List<AABB> result = Collections.synchronizedList(new ArrayList());
        ForkJoinPool.commonPool().invoke(new TreeColliderTask(result,root,aabb,0));
        return result;
    }
    
    
    


}
