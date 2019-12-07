/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap03.lwjgl07;

import org.joml.Matrix4f;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Renderer 
{
    private Shader shader;
    
    private Matrix4f projectionMatrix;
    
    public Renderer() {
    }

    public void init(int windowWidth,int windowHeight) throws Exception {

        shader = new Shader();
        
        String vertexShaderSource = 
        "#version 330\n" +
        "layout (location=0) in vec3 vertex;\n" +
        "layout (location=1) in vec2 inputTextureCoords;\n" +
        "                   out vec2 vertexTextureCoords;\n" +
        "uniform mat4 projectionMatrix;\n" +
        "uniform mat4 viewMatrix;\n" +
        "uniform mat4 transformMatrix;\n" +
        "void main() {\n" +
        "  gl_Position = projectionMatrix * viewMatrix * transformMatrix * vec4(vertex, 1.0);\n" +
        "  vertexTextureCoords = inputTextureCoords;\n" +
        "}";
        shader.createVertexShader(vertexShaderSource);
    
        String fragmentShaderSource = 
        "#version 330\n" +
        "in  vec2 vertexTextureCoords;\n" +
        "out vec4 color;\n" +
        "uniform sampler2D textureSampler;\n" +
        "void main() {\n" +
        "  color = texture(textureSampler, vertexTextureCoords);\n" +
        "}";
        shader.createFragmentShader(fragmentShaderSource);

        shader.link();

        float fov = (float) Math.toRadians(60.0f);
        float aspectRatio = ((float)windowWidth) / windowHeight;
        float zNear = 0.01f;
        float zFar = 1000.0f;
        projectionMatrix = new Matrix4f().perspective(fov, aspectRatio, zNear, zFar);   
    }

    public void render(Camera camera,ObjectMesh object) 
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        shader.bind();
        shader.setUniformValue("projectionMatrix",projectionMatrix);        
        shader.setUniformValue("viewMatrix",camera.getViewMatrix());
        shader.setUniformValue("transformMatrix",object.getTransformMatrix());

        shader.setUniformValue("textureSampler", 0);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, object.getTextureId());        
        
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
    }
}
