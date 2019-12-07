/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap03.lwjgl07;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {

    private final Vector3f position = new Vector3f();
    
    private final Vector3f rotation = new Vector3f();
    
    private static final Vector3f xaxe = new Vector3f(1,0,0);
    private static final Vector3f yaxe = new Vector3f(0,1,0);
    
    public Matrix4f getViewMatrix() {
        Matrix4f viewMatrix = new Matrix4f().identity();
        
        viewMatrix.rotate(rotation.x,xaxe);
        viewMatrix.rotate(rotation.y,yaxe);
        
        viewMatrix.translate(-position.x,-position.y,-position.z);
        
        return viewMatrix;
    }
    
    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
    }
    
    public void addPosition(float x, float y, float z) {
        if ( z != 0 ) {
            position.x += (float)Math.sin(rotation.y) * (-z);
            position.z += (float)Math.cos(rotation.y) * z;
        }
        if ( x != 0) {
            position.x += (float)Math.cos(rotation.y) * x;
            position.z += (float)Math.sin(rotation.y) * x;
        }
        position.y += y;
    }

    public Vector3f getRotation() {
        return rotation;
    }
    
    public void setRotation(float x, float y, float z) {
        rotation.x = x;
        rotation.y = y;
        rotation.z = z;
    }

    public void addRotation(float x, float y, float z) {
        rotation.x += x;
        rotation.y += y;
        rotation.z += z;
    }
    
}
