/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap03.awtfacade01;

public class Main {
    public static void main(String args[]) {
        GUIFacade gui = new AWTGUIFacade();
        gui.createWindow("GUI Facade");
    }       
}
