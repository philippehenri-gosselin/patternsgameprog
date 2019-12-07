/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap03.gamemodes02;

import examples.chap03.gamemodes02.gui.*;
import java.awt.Color;
import java.awt.event.KeyEvent;

public class WelcomeGameMode extends GameMode {
    
    private Image titleImage;
    
    private final static int windowWidth = 640;

    private final static int windowHeight = 480;

    public void init() {
        titleImage = gui.createImage("pacman_title.png");
        
        gui.createWindow(windowWidth, windowHeight, "Pacman");
    }
    
    public void handleInputs() {
        Keyboard keyboard = gui.getKeyboard();
        switch(keyboard.getLastPressedKey()) {
            case KeyEvent.VK_ESCAPE:
                gui.setClosingRequested(true);
                return;
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_ENTER:
                keyboard.consumeLastPressedKey();
                setGameMode(new MainMenuGameMode());
                return;
        }
    }

    public void update() {
    }

    public void render() {
        if (!gui.beginPaint())
            return;
        try {
            gui.clearBackground();
            gui.drawImage(titleImage, 
                (windowWidth-titleImage.getWidth())/2, 
                (windowHeight-titleImage.getHeight())/2
            );
        } finally {
            gui.endPaint();
        }
    }


}
