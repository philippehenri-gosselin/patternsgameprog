/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap03.lwjgl02;

import java.nio.FloatBuffer;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import org.lwjgl.system.MemoryUtil;

public class Renderer {
    private int vboId;

    private int vaoId;

    private Shader shader;
    
    public Renderer() {
    }

    public void init() throws Exception {

        shader = new Shader();
        
        String vertexShaderSource = 
        "#version 330\n" +
        "layout (location=0) in vec3 vertex;\n" +
        "void main() {\n" +
        "  gl_Position = vec4(vertex, 1.0);\n" +
        "}";
        shader.createVertexShader(vertexShaderSource);
    
        String fragmentShaderSource = 
        "#version 330\n" +
        "out vec4 color;\n" +
        "void main() {\n" +
        "  color = vec4(1.0, 1.0, 1.0, 1.0);\n" +
        "}";
        shader.createFragmentShader(fragmentShaderSource);

        shader.link();

        float[] vertex = new float[]{
            0.0f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f
        };

        FloatBuffer vertexBuffer = null;
        try {
            vertexBuffer = MemoryUtil.memAllocFloat(vertex.length);
            vertexBuffer.put(vertex).flip();

            vaoId = glGenVertexArrays();
            glBindVertexArray(vaoId);

            vboId = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, vboId);
            glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);
        } finally {
            if (vertexBuffer != null) {
                MemoryUtil.memFree(vertexBuffer);
            }
        }
    }

    public void render() 
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        shader.bind();

        glBindVertexArray(vaoId);
        glEnableVertexAttribArray(0);

        glDrawArrays(GL_TRIANGLES, 0, 3);

        glDisableVertexAttribArray(0);
        glBindVertexArray(0);

        shader.unbind();
    }

    public void dispose() {
        if (shader != null) {
            shader.dispose();
        }

        glDisableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(vboId);

        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }
}
