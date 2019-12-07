/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.collisions;

import examples.chap06.collisions01mt.ExhaustiveColliderMT;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;


public class ExhaustiveColliderMTTest {
    

    @Test
    public void testExhaustiveColliderMT() {
        ArrayList<AABB> boxes = new ArrayList();
        Random random = new Random();
        for (int i=0;i<1000000;i++) {
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
        
        List<AABB> result1 = new ExhaustiveCollider(boxes).collides(aabb);
        List<AABB> result2 = new ExhaustiveColliderMT(boxes).collides(aabb);
        assertEquals(result1, result2);
    }
    
}
