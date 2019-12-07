/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.features03.menu;

import examples.chap04.features03.gui.Keyboard;
import java.awt.event.KeyEvent;

public class MainMenu extends MenuGameMode {

    public MainMenu() {
        items.add("Play Pacman");
        items.add("Play a Ghost");
        items.add("Exit");
    }
    
    public void handleInputs() {
        Keyboard keyboard = gui.getKeyboard();
        switch(keyboard.getLastPressedKey()) {
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_ENTER:
                keyboard.consumeLastPressedKey();
                switch(selectedItem) {
                    case 0: 
                        getGameBuilder().setCharIndex(0);
                        setGameMode(new SelectDifficultyMenu());
                        break;
                    case 1:
                        setGameMode(new SelectGhostMenu());
                        break;
                    case 2:
                        gui.setClosingRequested(true);
                        break;
                }
                return;
        }
        super.handleInputs();
    }
}
