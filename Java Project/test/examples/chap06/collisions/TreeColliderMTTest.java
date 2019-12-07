/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.collisions;

import examples.chap06.collisions.AABB;
import examples.chap06.collisions.TreeCollider;
import examples.chap06.collisions02mt.TreeColliderMT;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;

public class TreeColliderMTTest {

 
    
    @Test
    public void testTreeColliderMT() {
        ArrayList<AABB> boxes = new ArrayList();
        Random random = new Random();
        for (int i=0;i<10000;i++) {
            int x0 = 50+random.nextInt(600);
            int y0 = 50+random.nextInt(400);
            int x1 = x0+10+random.nextInt(100);
            int y1 = y0+10+random.nextInt(100);
            boxes.add(new AABB(x0,y0,x1,y1));
        }
        int x0 = 50+random.nextInt(600);
        int y0 = 50+random.nextInt(400);
        int x1 = x0+10+random.nextInt(100);
        int y1 = y0+10+random.nextInt(100);
        AABB aabb = new AABB(x0,y0,x1,y1);        
        
        List<AABB> result1 = new TreeCollider(boxes).collides(aabb);
        List<AABB> result2 = new TreeColliderMT(boxes).collides(aabb);
        assertEquals(result1.size(),result2.size());
        for (AABB box: result1) {
            assertTrue(result2.contains(box));
        }
    }    
    
}
