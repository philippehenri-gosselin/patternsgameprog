/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap03.lwjgl03;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Renderer {
    private Shader shader;
    
    public Renderer() {
    }

    public void init() throws Exception {

        shader = new Shader();
        
        String vertexShaderSource = 
        "#version 330\n" +
        "layout (location=0) in vec3 vertex;\n" +
        "layout (location=1) in vec3 inputColor;\n" +
        "                   out vec3 vertexColor;\n" +
        "void main() {\n" +
        "  gl_Position = vec4(vertex, 1.0);\n" +
        "  vertexColor = inputColor;\n" +
        "}";
        shader.createVertexShader(vertexShaderSource);
    
        String fragmentShaderSource = 
        "#version 330\n" +
        "in  vec3 vertexColor;\n" +
        "out vec4 color;\n" +
        "void main() {\n" +
        "  color = vec4(vertexColor, 1.0);\n" +
        "}";
        shader.createFragmentShader(fragmentShaderSource);

        shader.link();
    }

    public void render(ObjectMesh object) 
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        shader.bind();

        glBindVertexArray(object.getVaoId());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glDrawElements(GL_TRIANGLES, object.getVertexCount(), GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        shader.unbind();
    }

    public void dispose() {
        if (shader != null) {
            shader.dispose();
        }

        glDisableVertexAttribArray(0);
    }
}
