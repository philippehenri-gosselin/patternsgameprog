/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap03.lwjgl03;

import static org.lwjgl.opengl.GL20.*;

public class Shader {

    private final int shaderId;

    private int vertexShaderId;

    private int fragmentShaderId;

    public Shader() {
        shaderId = glCreateProgram();
        if (shaderId == 0) {
            throw new RuntimeException("Error when creating the shader");
        }
    }

    protected int createShader(String code, int type) {
        int id = glCreateShader(type);
        if (id == 0) {
            throw new RuntimeException("Error when creating the shader");
        }

        glShaderSource(id, code);
        glCompileShader(id);

        if (glGetShaderi(id, GL_COMPILE_STATUS) == 0) {
            throw new RuntimeException("Error when compiling the shader");
        }

        glAttachShader(shaderId, id);
        return id;
    }

    public void createVertexShader(String code) {
        vertexShaderId = createShader(code, GL_VERTEX_SHADER);
    }

    public void createFragmentShader(String code) {
        fragmentShaderId = createShader(code, GL_FRAGMENT_SHADER);
    }
    
    public void link() {
        glLinkProgram(shaderId);
        if (glGetProgrami(shaderId, GL_LINK_STATUS) == 0) {
            throw new RuntimeException("Error when linking the shader");
        }

        if (vertexShaderId != 0) {
            glDetachShader(shaderId, vertexShaderId);
        }
        if (fragmentShaderId != 0) {
            glDetachShader(shaderId, fragmentShaderId);
        }

        glValidateProgram(shaderId);
        if (glGetProgrami(shaderId, GL_VALIDATE_STATUS) == 0) {
            throw new RuntimeException("Error when validating  the shader");
        }

    }

    public void bind() {
        glUseProgram(shaderId);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void dispose() {
        unbind();
        if (shaderId != 0) {
            glDeleteProgram(shaderId);
        }
    }
}
