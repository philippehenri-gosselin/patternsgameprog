/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap03.lwjgl05;

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
    
    private Camera camera = new Camera();
    
    private KeyboardHandler keyboard;
    
    private Mouse mouse;
    
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
        
        keyboard = new KeyboardHandler(windowHandle);
        mouse = new Mouse(windowHandle);

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
        glEnable(GL_DEPTH_TEST);
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        
        renderer.init(windowWidth,windowHeight);
        
        float[] vertex = new float[]{
            -0.5f,  0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
             0.5f, -0.5f, -0.5f,
             0.5f,  0.5f, -0.5f,
            -0.5f,  0.5f,  0.5f,
             0.5f,  0.5f,  0.5f,
            -0.5f, -0.5f,  0.5f,
             0.5f, -0.5f,  0.5f,
        };
        float[] colors = new float[]{
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f,
            1.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            1.0f, 1.0f, 0.0f,
        };
        int[] index = new int[]{
            0, 1, 3, 3, 1, 2,
            4, 0, 3, 5, 4, 3,
            3, 2, 7, 5, 3, 7,
            6, 1, 0, 6, 0, 4,
            2, 1, 6, 2, 6, 7,
            7, 6, 4, 7, 4, 5,
        };
        object = new ObjectMesh(vertex, colors, index);        
        
        camera.addPosition(1.5f,0,2.0f);
        camera.addRotation(0,-0.60f, 0);
    }

    private int prevMouseX;
    private int prevMouseY;
    private void handleInputs() 
    {
        if (keyboard.isKeyPressed(GLFW_KEY_ESCAPE)) {
            glfwSetWindowShouldClose(windowHandle, true);
        }
        if (keyboard.isKeyPressed(GLFW_KEY_UP)
         || keyboard.isKeyPressed(GLFW_KEY_W)
         || keyboard.isKeyPressed(GLFW_KEY_Z)) {
            camera.addPosition(0, 0, -0.01f);
        }
        else if (keyboard.isKeyPressed(GLFW_KEY_DOWN)
              || keyboard.isKeyPressed(GLFW_KEY_S)) {
            camera.addPosition(0, 0, 0.01f);
        }
        if (keyboard.isKeyPressed(GLFW_KEY_LEFT)
         || keyboard.isKeyPressed(GLFW_KEY_Q)
         || keyboard.isKeyPressed(GLFW_KEY_A)) {
            camera.addPosition(-0.01f, 0, 0);
        }
        else if (keyboard.isKeyPressed(GLFW_KEY_RIGHT)
              || keyboard.isKeyPressed(GLFW_KEY_D)) {
            camera.addPosition(0.01f, 0, 0);
        }
        
        int mouseDiffX = mouse.getX() - prevMouseX;
        int mouseDiffY = mouse.getY() - prevMouseY;
        if (mouse.isButtonPressed(2)) {
            camera.addRotation(mouseDiffY * 0.005f, mouseDiffX * 0.005f, 0);
        }
        prevMouseX = mouse.getX();
        prevMouseY = mouse.getY();
    }
    
    private void run() 
    {
        while (!glfwWindowShouldClose(windowHandle)) 
        {
            handleInputs();
            
            renderer.render(camera,object);
            
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
