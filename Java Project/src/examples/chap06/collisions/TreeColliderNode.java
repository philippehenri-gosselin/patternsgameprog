/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.collisions;

import java.util.ArrayList;
import java.util.List;

public class TreeColliderNode {

    private TreeColliderNode parent;

    private TreeColliderNode leftChild;

    private TreeColliderNode rightChild;

    private AABB aabb;
    
    public TreeColliderNode(AABB aabb) {
        this.aabb = aabb;
    }
    
    public TreeColliderNode(TreeColliderNode leftChild,TreeColliderNode rightChild) {
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        leftChild.parent = this;
        rightChild.parent = this;
        aabb = new AABB(leftChild.aabb,rightChild.aabb);
    }
    
    public boolean isLeaf() {
        return leftChild == null && rightChild == null;
    }
    
    public void updateAABB() {
        aabb.union(leftChild.aabb,rightChild.aabb);
        if (parent != null) {
            parent.updateAABB();
        }
    }

    public TreeColliderNode getParent() {
        return parent;
    }

    public void setParent(TreeColliderNode parent) {
        this.parent = parent;
    }

    public TreeColliderNode getLeftChild() {
        return leftChild;
    }

    public TreeColliderNode getRightChild() {
        return rightChild;
    }

    public void setChildren(TreeColliderNode leftChild,TreeColliderNode rightChild) {
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        leftChild.setParent(this);
        rightChild.setParent(this);
    }
    
    public void changeChild(TreeColliderNode oldChild,TreeColliderNode newChild) {
        if (leftChild == oldChild) {
            leftChild = newChild;
            newChild.parent = this;
            updateAABB();
        }
        else if (rightChild == oldChild) {
            rightChild = newChild;
            newChild.parent = this;
            updateAABB();
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public AABB getAabb() {
        return aabb;
    }

    public void setAabb(AABB aabb) {
        this.aabb = aabb;
    }
    
    public void collides(List<AABB> result, AABB aabb) {
        if (isLeaf()) {
            if (this.aabb.collides(aabb)) {
                result.add(this.aabb);
            }
        }
        else {
            if (!this.aabb.collides(aabb))
                return;
            if (leftChild != null) {
                leftChild.collides(result, aabb);
            }
            if (rightChild != null) {
                rightChild.collides(result, aabb);
            }
        }
    }    
    
    public boolean isValid() {
        if (leftChild != null) {
            if (!aabb.contains(leftChild.aabb))
                return false;
        }
        if (rightChild != null) {
            if (!aabb.contains(rightChild.aabb))
                return false;
        }
        return true;
    }
    
    public TreeColliderNode find(AABB aabb) {
        if (isLeaf()) {
            if (aabb.equals(this.aabb)) {
                return this;
            }
        }
        else {
            if (leftChild != null) {
                TreeColliderNode node = leftChild.find(aabb);
                if (node != null)
                    return node;
            }
            if (rightChild != null) {
                TreeColliderNode node = rightChild.find(aabb);
                if (node != null)
                    return node;
            }
        }
        return null;
    }

    public void show(int depth) {
        for(int i=0;i<depth;i++)
            System.out.print("  ");
        System.out.println(aabb);
        if (leftChild != null) {
            leftChild.show(depth+1);
        }
        if (rightChild != null) {
            rightChild.show(depth+1);
        }
    }

}
