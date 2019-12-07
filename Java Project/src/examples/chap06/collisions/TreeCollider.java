/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.collisions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TreeCollider implements Collider {

    protected TreeColliderNode root;
    
    public TreeCollider(List<AABB> boxes) {
       for (int i=0;i<boxes.size();i++) {
            add(boxes.get(i));
        }
    }

    private void insert(TreeColliderNode node, TreeColliderNode parent) {
        if (parent.isLeaf()) {
            TreeColliderNode grandParent = parent.getParent();
            TreeColliderNode newParent = new TreeColliderNode(parent,node);
            if (grandParent == null) {
                root = newParent;
            }
            else {
                grandParent.changeChild(parent,newParent);
            }
        }
        else {
            AABB parentLeftBox = parent.getLeftChild().getAabb();
            AABB parentRightBox = parent.getRightChild().getAabb();
            long leftVolume = parentLeftBox.volumeUnion(node.getAabb()) - parentLeftBox.volume();
            long rightVolume = parentRightBox.volumeUnion(node.getAabb()) - parentRightBox.volume();
            if (leftVolume < rightVolume) {
                insert(node,parent.getLeftChild());
            }
            else {
                insert(node,parent.getRightChild());
            }
        }
    }
    
    public void add(AABB aabb) {
        if (root == null) {
            root = new TreeColliderNode(aabb);
        }
        else {
            insert(new TreeColliderNode(aabb),root);
        }
    }
    
    public boolean stores(AABB aabb) {
        if (root != null) {
            return root.find(aabb) != null;
        }
        return false;
    }
    
    public boolean isValid() {
        if (root != null) {
            return root.isValid();
        }
        return true;
    }    
    
    public List<AABB> collides(AABB aabb) {
        ArrayList<AABB> result = new ArrayList();
        if (root != null) {
            root.collides(result,aabb);
        }
        return result;
    }
    
    public void show() {
        if (root != null) {
            root.show(0);
        }
    }

    public static void main(String[] args) {
        ArrayList<AABB> boxes = new ArrayList();
        Random random = new Random(0);
        for (int i=0;i<1000000;i++) {
            int x0 = 50+random.nextInt(600);
            int y0 = 50+random.nextInt(400);
            int x1 = x0+1+random.nextInt(10);
            int y1 = y0+1+random.nextInt(10);
            boxes.add(new AABB(x0,y0,x1,y1));
        }
        int x0 = 50+random.nextInt(600);
        int y0 = 50+random.nextInt(400);
        int x1 = x0+1+random.nextInt(10);
        int y1 = y0+1+random.nextInt(10);
        AABB aabb = new AABB(x0,y0,x1,y1);
        
        //List<AABB> result1 = new TreeCollider(boxes).collides(aabb);
        List<AABB> result2 = new TreeCollider(boxes).collides(aabb);
    }
    
}
