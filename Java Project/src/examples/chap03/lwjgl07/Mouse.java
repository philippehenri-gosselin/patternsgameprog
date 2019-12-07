/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap03.lwjgl07;

import static org.lwjgl.glfw.GLFW.*;

public class Mouse {
    private boolean[] buttons;
    private int x;
    private int y;

    public Mouse(long windowHandle) {
        buttons = new boolean[4];
        
        glfwSetCursorPosCallback(windowHandle, (handle, xpos, ypos) -> {
            x = (int)xpos;
            y = (int)ypos;
        });
        /*glfwSetCursorEnterCallback(windowHandle, (handle, entered) -> {
            inWindow = entered;
        });*/
        glfwSetMouseButtonCallback(windowHandle, (handle, button, action, mode) -> {
            buttons[0] = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS;
            buttons[1] = button == GLFW_MOUSE_BUTTON_3 && action == GLFW_PRESS;
            buttons[2] = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS;
        });
    }

    public boolean isButtonPressed(int button) {
        if (button >= buttons.length) {
            return false;
        }
        return buttons[button];
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
