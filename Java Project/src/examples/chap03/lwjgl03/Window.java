/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap03.lwjgl03;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {

    private long windowHandle;

    private int windowWidth = 800;

    private int windowHeight = 600;

    private Renderer renderer = new Renderer();

    private ObjectMesh object;

    private void init() throws Exception {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException("Error when initializing GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

        windowHandle = glfwCreateWindow(windowWidth, windowHeight, "Display and controls with LWJGL", NULL, NULL);
        if (windowHandle == NULL) {
            throw new RuntimeException("Error when creating the window");
        }

        glfwSetKeyCallback(windowHandle, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(window, true);
            }
        });

        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(
                windowHandle,
                (vidmode.width() - windowWidth) / 2,
                (vidmode.height() - windowHeight) / 2
        );

        glfwMakeContextCurrent(windowHandle);
        glfwSwapInterval(1);

        glfwShowWindow(windowHandle);

        GL.createCapabilities();

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        renderer.init();

        // Exemple plus simple avec un triangle
        /*float[] vertex = new float[]{
            0.0f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f
        };
        float[] colors = new float[]{
            1.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 1.0f,
        };
        int[] index = new int[]{
            0, 1, 2,
        };*/
        // Autre exemple plus simple avec un rectangle
        /*float[] vertex = new float[]{
            -0.5f,  0.5f, 0.0f,
             0.5f,  0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
             0.5f, -0.5f, 0.0f,
        };
        float[] colors = new float[]{
            1.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f,
        };
        int[] index = new int[]{
            0, 1, 2,
            1, 2, 3,
        };*/
        int vertexCount = 11;
        float[] vertex = new float[3 * vertexCount];
        float[] colors = new float[3 * vertexCount];
        int[] index = new int[3 * (vertexCount - 1)];
        vertex[0] = 0;
        vertex[1] = 0;
        vertex[2] = 0;
        colors[0] = 0.0f;
        colors[1] = 1.0f;
        colors[2] = 1.0f;
        for (int i = 1; i < vertexCount; i++) {
            double ip = (2 * Math.PI * (i - 1)) / (vertexCount - 1);
            vertex[3 * i + 0] = (float) (0.5 * Math.cos(ip));
            vertex[3 * i + 1] = (float) (0.5 * Math.sin(ip));

            colors[3 * i + 0] = ((float) (i - 1)) / (vertexCount - 1);
            colors[3 * i + 1] = 0f;
            colors[3 * i + 2] = 0f;

            index[3 * (i - 1) + 0] = 0;
            index[3 * (i - 1) + 1] = i;
            index[3 * (i - 1) + 2] = i + 1;
        }
        index[3 * (vertexCount - 2) + 2] = 1;

        object = new ObjectMesh(vertex, colors, index);
    }

    private void run() {
        while (!glfwWindowShouldClose(windowHandle)) {
            renderer.render(object);

            glfwSwapBuffers(windowHandle);
            glfwPollEvents();
        }
    }

    private void dispose() {
        renderer.dispose();
        object.dispose();

        glfwFreeCallbacks(windowHandle);
        glfwDestroyWindow(windowHandle);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public static void main(String[] args) throws Exception {
        Window window = new Window();
        window.init();
        window.run();
        window.dispose();
    }

}
