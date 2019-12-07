/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.collisions01mt;

import examples.chap06.collisions.AABB;
import examples.chap06.collisions.Collider;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

public class ExhaustiveColliderMT implements Collider {

    private final ArrayList<AABB> boxes;

    public ExhaustiveColliderMT(ArrayList<AABB> boxes) {
        this.boxes = boxes;
    }

    public List<AABB> collides(AABB aabb) {
        int chunkSize = boxes.size() / 16;
        int chunkIndex = 0;        
        ArrayList<Future<List<AABB>>> futures = new ArrayList();
        while (chunkIndex < boxes.size()) {
            int fromIndex = chunkIndex;
            int toIndex = chunkIndex + chunkSize;
            if (toIndex >= boxes.size()) {
                toIndex = boxes.size();
            }
            List<AABB> subList = boxes.subList(fromIndex, toIndex);
            futures.add(ForkJoinPool.commonPool().submit(new ColliderThread(subList, aabb)));
            chunkIndex += chunkSize;
        }

        ArrayList<AABB> result = new ArrayList<AABB>();
        for (Future<List<AABB>> future : futures) {
            try {
                List<AABB> subResult = future.get();
                for (AABB box : subResult) {
                    result.add(box);
                }
            } catch (Exception ex) {
                throw new RuntimeException();
            }
        }
        return result;
    }

}
