/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.network02.menu;

import examples.chap06.network02.GameOverMode;
import examples.chap06.network02.ReplayGameMode;
import examples.chap06.network02.gui.Keyboard;
import java.awt.event.KeyEvent;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainMenu extends MenuGameMode {

    public MainMenu() {
        items.add("Play Pacman");
        items.add("Play a Ghost");
        items.add("Multiplayer");
        items.add("Replay the last game");
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
                        setGameMode(new MultiplayerMenu());
                        break;
                    case 3:
                        if (Files.exists(Paths.get("replay.json"))) {
                            setGameMode(new ReplayGameMode());
                        }
                        else {
                            setGameMode(new GameOverMode("Il n'y a pas de fichier replay.json"));
                        }
                        break;
                    case 4:
                        gui.setClosingRequested(true);
                        break;
                }
                return;
        }
        super.handleInputs();
    }
}
