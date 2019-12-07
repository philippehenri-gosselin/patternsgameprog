/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap06.collisions02mt;

import examples.chap06.collisions.AABB;
import examples.chap06.collisions.TreeColliderNode;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class TreeColliderTask extends RecursiveAction {

    private final List<AABB> result;
    
    private final TreeColliderNode node;

    private final AABB aabb;
    
    private final int depth;

    public TreeColliderTask(List<AABB> result,TreeColliderNode node,AABB aabb,int depth) {
        this.result = result;
        this.node = node;
        this.aabb = aabb;
        this.depth = depth;
    }
    
    protected void compute() {
        if (node.isLeaf()) {
            if (node.getAabb().collides(aabb)) {
                result.add(node.getAabb());
            }
        }
        else if (depth >= 10) {
            node.collides(result, aabb);
        }
        else {
            if (!node.getAabb().collides(aabb)) {
                return;
            }
            TreeColliderTask leftTask = new TreeColliderTask(result,node.getLeftChild(),aabb,depth+1);
            TreeColliderTask rightTask = new TreeColliderTask(result,node.getRightChild(),aabb,depth+1);
            leftTask.fork();
            rightTask.compute();
            leftTask.join();
        }
    }

}
