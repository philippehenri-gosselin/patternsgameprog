/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.ai02.menu;

import examples.chap05.ai02.gui.Keyboard;
import java.awt.event.KeyEvent;

public class SelectGhostMenu extends MenuGameMode {

    public SelectGhostMenu() {
        items.add("Play Blinky");
        items.add("Play Pinky");
        items.add("Play Inky");
        items.add("Play Clyde");
    }
    
    public void handleInputs() {
        Keyboard keyboard = gui.getKeyboard();
        switch(keyboard.getLastPressedKey()) {
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_ENTER:
                keyboard.consumeLastPressedKey();
                getGameBuilder().setCharIndex(selectedItem+1);
                setGameMode(new SelectDifficultyMenu());
                return;
        }
        super.handleInputs();

    }
}
