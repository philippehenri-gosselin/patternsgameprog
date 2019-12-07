/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.features01;

import examples.chap04.features01.rules.commands.InitCommand;

public class GameBuilder {

    private int charIndex;

    private int difficulty;

    public PlayGameMode createGame() {
        InitCommand initCommand;
        if (charIndex == 0) {
            if (difficulty == 0) 
                initCommand = new InitCommand(2, 4);
            else if (difficulty == 1)
                initCommand = new InitCommand(2, 3);
            else
                initCommand = new InitCommand(2, 2);
        }
        else {
            if (difficulty == 0) 
                initCommand = new InitCommand(4, 2);
            else if (difficulty == 1)
                initCommand = new InitCommand(3, 2);
            else
                initCommand = new InitCommand(2, 2);
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
