/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap03.lwjgl07;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import org.lwjgl.system.MemoryUtil;

public class ObjectMesh {
    
    private final int vaoId;

    private final int vertexVboId;

    private final int textureVboId;

    private final int indexVboId;

    private final int vertexCount;
    
    private Vector3f position = new Vector3f();
    
    private Vector3f rotation = new Vector3f();
    
    private float scale = 1;
    
    private Texture texture;

    public ObjectMesh(float[] vertex, float[] textureCoords, int[] index, Texture texture) {
        this.texture = texture;
        FloatBuffer vertexBuffer = null;
        FloatBuffer textureCoordsBuffer = null;
        IntBuffer indexBuffer = null;
        try {
            vertexCount = index.length;

            vaoId = glGenVertexArrays();
            glBindVertexArray(vaoId);

            vertexVboId = glGenBuffers();
            vertexBuffer = MemoryUtil.memAllocFloat(vertex.length);
            vertexBuffer.put(vertex).flip();
            glBindBuffer(GL_ARRAY_BUFFER, vertexVboId);
            glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

            textureVboId = glGenBuffers();
            textureCoordsBuffer = MemoryUtil.memAllocFloat(textureCoords.length);
            textureCoordsBuffer.put(textureCoords).flip();
            glBindBuffer(GL_ARRAY_BUFFER, textureVboId);
            glBufferData(GL_ARRAY_BUFFER, textureCoordsBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

            indexVboId = glGenBuffers();
            indexBuffer = MemoryUtil.memAllocInt(index.length);
            indexBuffer.put(index).flip();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexVboId);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW);

            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);
        } finally {
            if (vertexBuffer != null) {
                MemoryUtil.memFree(vertexBuffer);
            }
            if (textureCoordsBuffer != null) {
                MemoryUtil.memFree(textureCoordsBuffer);
            }
            if (indexBuffer != null) {
                MemoryUtil.memFree(indexBuffer);
            }
        }
    }
    
    public int getTextureId() {
        return texture.getId();
    }
    
    public Matrix4f getTransformMatrix() {
        Matrix4f transformMatrix = new Matrix4f().identity();
        transformMatrix.translate(position);
        transformMatrix.rotateX(rotation.x);
        transformMatrix.rotateY(rotation.y);
        transformMatrix.rotateZ(rotation.z);
        transformMatrix.scale(scale);
        return transformMatrix;
    }
    
    public int getVaoId() {
        return vaoId;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void dispose() {
        texture.dispose();
        
        glDisableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(vertexVboId);
        glDeleteBuffers(textureVboId);
        glDeleteBuffers(indexVboId);

        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }

}
