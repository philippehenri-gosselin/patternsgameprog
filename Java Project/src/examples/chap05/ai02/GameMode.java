/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.ai02;

import examples.chap05.ai02.gui.GUIFacade;

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
    
    public void clearGameModes() {
        parent.clearGameModes();
    }
    
    public GameBuilder getGameBuilder() {
        return parent.getGameBuilder();
    }
    
    public abstract void handleInputs();
    
    public abstract void update();
    
    public abstract void render(long time);
    
    public abstract void init();
    
}
