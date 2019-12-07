/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap03.lwjgl07;

import org.joml.Vector3f;
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
        
        float[] vertex = {
            0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f,
            0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f,
            -0.5f, 0.5f, 0.5f, -0.5f, 0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f, 0.5f, 0.5f, -0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f,
            0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f, -0.5f,
        };
        int[] index = {
            0, 1, 2, 0, 2, 3,
            4, 5, 6, 4, 6, 7,
            8, 9, 10, 8, 10, 11,
            12, 13, 14, 12, 14, 15,
            16, 17, 18, 16, 18, 19,
            20, 21, 22, 20, 22, 23,
        };
        float[] textureCoords = {
            1, 0, 0, 0, 0, 1, 1, 1,
            0, 0, 0, 1, 1, 1, 1, 0,
            1, 1, 0, 1, 0, 0, 1, 0,
            0, 0, 1, 0, 1, 1, 0, 1,
            0, 1, 0, 0, 1, 0, 1, 1,
            0, 0, 1, 0, 1, 1, 0, 1,
        };
        
        Texture texture = new Texture();
        texture.load("wall_texture.jpg");
        object = new ObjectMesh(vertex, textureCoords, index, texture);
        
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
    
    private boolean translateUp;
    private void update() {
        Vector3f position = object.getPosition();
        if (translateUp) {
            position.y += 0.01f;
            if (position.y >= 1.0f) {
                translateUp = false;
            }
        }
        else {
            position.y -= 0.01f;
            if (position.y <= 0.0f) {
                translateUp = true;
            }
        }
        object.setPosition(position);
        
        Vector3f rotation = object.getRotation();
        rotation.x += 0.01f;
        if (rotation.x > 2*Math.PI) {
            rotation.x = 0;
        }
        rotation.y += 0.01f;
        if (rotation.y > 2*Math.PI) {
            rotation.y = 0;
        }
        rotation.z += 0.01f;
        if (rotation.z > 2*Math.PI) {
            rotation.z = 0;
        }
        object.setRotation(rotation);
    }
    
    private void run() 
    {
        while (!glfwWindowShouldClose(windowHandle)) 
        {
            handleInputs();
            
            update();
            
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
