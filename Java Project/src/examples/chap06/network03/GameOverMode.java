/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.network03;

import examples.chap06.network03.gui.Keyboard;
import examples.chap06.network03.menu.MainMenu;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;

public class GameOverMode extends GameMode {

    private String msg;
    
    private final static int windowWidth = 640;

    private final static int windowHeight = 480;
    
    public GameOverMode(String msg) {
        this.msg = msg;
    }
    
    public void init() {
        gui.createWindow(windowWidth, windowHeight, "Victoire");
    }

    public void handleInputs() {
        Keyboard keyboard = gui.getKeyboard();
        switch(keyboard.getLastPressedKey()) {
            case KeyEvent.VK_ESCAPE:
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_ENTER:
                keyboard.consumeLastPressedKey();
                clearGameModes();
                setGameMode(new MainMenu());
                return;
        }
    }

    public void update() {
    }

    public void render(long time) {
        if (!gui.beginPaint())
            return;
        try {
            gui.clearBackground();
            gui.setColor(Color.white);
            gui.setTextSize(64);
            Dimension dimension = gui.getTextMetrics(msg);
            gui.drawText(msg, 
                (windowWidth-dimension.width)/2, (windowHeight-dimension.height)/2, 
                dimension.width, dimension.height);
        } finally {
            gui.endPaint();
        }
    }
}
