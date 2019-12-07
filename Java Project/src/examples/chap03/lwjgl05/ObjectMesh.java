/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap03.lwjgl05;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import org.lwjgl.system.MemoryUtil;

public class ObjectMesh {
    
    private final int vaoId;

    private final int vertexVboId;

    private final int colorVboId;

    private final int indexVboId;

    private final int vertexCount;

    public ObjectMesh(float[] vertex, float[] colors, int[] index) {
        FloatBuffer vertexBuffer = null;
        FloatBuffer colorBuffer = null;
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

            colorVboId = glGenBuffers();
            colorBuffer = MemoryUtil.memAllocFloat(colors.length);
            colorBuffer.put(colors).flip();
            glBindBuffer(GL_ARRAY_BUFFER, colorVboId);
            glBufferData(GL_ARRAY_BUFFER, colorBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);

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
            if (colorBuffer != null) {
                MemoryUtil.memFree(colorBuffer);
            }
            if (indexBuffer != null) {
                MemoryUtil.memFree(indexBuffer);
            }
        }
    }

    public int getVaoId() {
        return vaoId;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public void dispose() {
        glDisableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(vertexVboId);
        glDeleteBuffers(colorVboId);
        glDeleteBuffers(indexVboId);

        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }

}
