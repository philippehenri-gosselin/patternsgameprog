/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.draw02;

import examples.chap04.draw02.gui.GUIFacade;
import java.util.LinkedList;

public abstract class GameMode {

    protected GUIFacade gui;
    
    private Main parent;

    public void setParent(Main parent) {
        this.parent = parent;
    }    
    
    public void setGUI(GUIFacade gui) {
        this.gui = gui;
    }

    public void setGameMode(GameMode mode) {
        parent.setGameMode(mode);
    }
    
    public void setPreviousGameMode() {
        parent.setPreviousGameMode();
    }    
    
    public abstract void handleInputs();
    
    public abstract void update();
    
    public abstract void render();
    
    public abstract void init();
    
}
