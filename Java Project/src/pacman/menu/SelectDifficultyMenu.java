/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.menu;

import pacman.GameBuilder;
import pacman.PlayGameMode;
import pacman.gui.Keyboard;
import java.awt.event.KeyEvent;

public class SelectDifficultyMenu extends MenuGameMode {

    public SelectDifficultyMenu() {
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
                GameBuilder builder = getGameBuilder();
                builder.setDifficulty(selectedItem);
                setGameMode(builder.createGame());
                return;
        }
        super.handleInputs();
    }
}
