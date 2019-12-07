/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap03.gamemodes00;

import examples.chap03.gamemodes00.gui.GUIFacade;

public interface GameMode {
    
    public abstract void setGUI(GUIFacade gui);

    public abstract void handleInputs();
    
    public abstract void update();
    
    public abstract void render();
    
    public abstract void init();


}
