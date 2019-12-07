/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.ai03;

import examples.chap05.ai03.rules.commands.LoadLevelCommand;

public class GameBuilder {

    private int charIndex;

    private int difficulty;

    public PlayGameMode createGame() {
        LoadLevelCommand initCommand;
        if (charIndex == 0) {
            if (difficulty == 0) 
                initCommand = new LoadLevelCommand("level.tmx",2, 4);
            else if (difficulty == 1)
                initCommand = new LoadLevelCommand("level.tmx",2, 3);
            else
                initCommand = new LoadLevelCommand("level.tmx",2, 2);
        }
        else {
            if (difficulty == 0) 
                initCommand = new LoadLevelCommand("level.tmx",4, 2);
            else if (difficulty == 1)
                initCommand = new LoadLevelCommand("level.tmx",3, 2);
            else
                initCommand = new LoadLevelCommand("level.tmx",2, 2);
        }
        PlayGameMode game = new PlayGameMode(initCommand);
        game.setCurrentChar(charIndex);
        return game;
    }

    public int getCharIndex() {
        return charIndex;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setCharIndex(int charIndex) {
        this.charIndex = charIndex;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
