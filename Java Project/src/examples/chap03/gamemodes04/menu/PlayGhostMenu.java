/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap03.gamemodes04.menu;

import examples.chap03.gamemodes04.PlayGameMode;
import examples.chap03.gamemodes04.gui.Keyboard;
import java.awt.event.KeyEvent;

public class PlayGhostMenu extends MenuGameMode {

    public PlayGhostMenu() {
        items.add("Easy");
        items.add("Normal");
        items.add("Hard");
    }
        
    public void handleInputs() {
        Keyboard keyboard = gui.getKeyboard();
        switch(keyboard.getLastPressedKey()) {
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_ENTER:
                keyboard.consumeLastPressedKey();
                setGameMode(new PlayGameMode());
                return;
        }
        super.handleInputs();
    }
    
}
