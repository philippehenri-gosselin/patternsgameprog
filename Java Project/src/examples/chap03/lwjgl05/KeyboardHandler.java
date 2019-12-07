/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap03.lwjgl05;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWKeyCallback;

public class KeyboardHandler extends GLFWKeyCallback {

    public boolean[] keys = new boolean[0x10000];
    
    public KeyboardHandler(long windowHandle) {
        glfwSetKeyCallback(windowHandle,this);
    }

    public void invoke(long window, int key, int scancode, int action, int mods) {
        if (key < keys.length) {
            keys[key] = action != GLFW_RELEASE;
        }
    }

    public boolean isKeyPressed(int keyCode) {
        if (keyCode >= keys.length)
            return false;
        return keys[keyCode];
    }

}
