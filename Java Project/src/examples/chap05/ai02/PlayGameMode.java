/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.ai02;

import examples.chap05.ai02.ai.AI;
import examples.chap05.ai02.ai.CommandsLister;
import examples.chap05.ai02.ai.DefaultCommandsLister;
import examples.chap05.ai02.ai.RandomAI;
import examples.chap05.ai02.ai.ExplorationAI;
import examples.chap05.ai02.gui.Keyboard;
import examples.chap05.ai02.state.Characters;
import examples.chap05.ai02.state.Direction;
import examples.chap05.ai02.state.State;
import examples.chap05.ai02.render.Renderer;
import examples.chap05.ai02.rules.commands.DirectionCommand;
import examples.chap05.ai02.rules.Rules;
import examples.chap05.ai02.rules.commands.Command;
import examples.chap05.ai02.state.Pacman;
import examples.chap05.ai02.state.PacmanStatus;
import examples.chap05.ai02.state.immutable.ImmutableState;
import examples.chap05.ai02.state.mutable.MutableState;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Random;
import java.util.logging.Logger;

public class PlayGameMode extends GameMode {

    private MutableState state;
    
    private Renderer renderer;
    
    private Rules rules;
    
    private int currentChar = 0; 
    
    private Command initCommand;
    
    private AI[] ais = new AI[5];    
    
    public PlayGameMode(Command initCommand) {
        this.initCommand = initCommand;
    }   
    
    public void setCurrentChar(int currentChar) {
        this.currentChar = currentChar;
    }
    
    public void init() 
    {
        state = new MutableState();
        
        renderer = new Renderer(gui);
        state.registerObserver(renderer);
        
        Random random = new Random();
        CommandsLister commandsLister = new DefaultCommandsLister();
        ImmutableState imState = new ImmutableState(state);
        ais[0] = new ExplorationAI(imState,0,commandsLister,random);
        for(int charIndex=1;charIndex<5;charIndex++) {
            ais[charIndex] = new RandomAI(imState,charIndex,commandsLister,random);
        }
        
        rules = new Rules(state);
        rules.addCommand(0, initCommand);
        rules.update();
    }
    
    private State savedState;
    public void handleInputs() {
        Characters chars = state.getChars();
        Keyboard keyboard = gui.getKeyboard();
        switch(keyboard.getLastPressedKey()) {
            case KeyEvent.VK_ESCAPE:
                keyboard.consumeLastPressedKey();
                setPreviousGameMode();
                return;
            case KeyEvent.VK_F1:
                currentChar = 0;
                break;
            case KeyEvent.VK_F2:
                if (chars.size() > 1)
                    currentChar = 1;
                break;
            case KeyEvent.VK_F3:
                if (chars.size() > 2)
                    currentChar = 2;
                break;
            case KeyEvent.VK_F4:
                if (chars.size() > 3)
                    currentChar = 3;
                break;
            case KeyEvent.VK_F5:
                if (chars.size() > 4)
                    currentChar = 4;
                break;
            case KeyEvent.VK_F6:
                keyboard.consumeLastPressedKey();
                state.save("save.ser");
                savedState = state.clone();
                break;
            case KeyEvent.VK_F7:
                keyboard.consumeLastPressedKey();
                if (!new File("save.ser").exists())
                    break;
                state = State.load("save.ser");
                state.registerObserver(renderer);
                rules = new Rules(state);
                state.notityStateChanged();
                if (savedState != null) {
                    if (!savedState.equals(state)) {
                        Logger.getLogger("global").warning("Incohérence dans l'état sauvegardé");
                    }
                }
                break;
            case KeyEvent.VK_BACK_SPACE:
                keyboard.consumeLastPressedKey();
                //rules.addCommand(0,new InitCommand());
                rollback = true;
                break;
        }
        

        if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)) {
            rules.addCommand(currentChar,new DirectionCommand(currentChar,Direction.EAST));
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)) {
            rules.addCommand(currentChar,new DirectionCommand(currentChar,Direction.WEST));
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)) {
            rules.addCommand(currentChar,new DirectionCommand(currentChar,Direction.SOUTH));
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_UP)) {
            rules.addCommand(currentChar,new DirectionCommand(currentChar,Direction.NORTH));
        }
        
    }
    
    private boolean rollback = false;
    private long lastUpdate;
    public void update() {
        long now = System.nanoTime();

        if ( (now - lastUpdate) >= state.getEpochDuration()) 
        {
            lastUpdate = now;

            if (rules.getActions().isEmpty()) {
                rollback = false;
            }
            
            if (rollback) {
                rules.rollback();                
            }
            else {
                
                for(int charIndex=0;charIndex<5;charIndex++) {
                    if (charIndex == currentChar)
                        continue;
                    Command command = ais[charIndex].createCommand();
                    if (command != null) {
                        rules.addCommand(charIndex, command);
                    }
                }

                rules.addPassiveCommands();                
                rules.update();                

                if (state.getGumCount() == 0) {
                    if (currentChar == 0) {
                        setGameMode(new GameOverMode("Victory !"));
                    }
                    else {
                        setGameMode(new GameOverMode("You failed!"));
                    }
                }
                Characters chars = state.getChars();
                Pacman pacman = chars.getPacman();
                if (pacman != null && pacman.getStatus() == PacmanStatus.DEAD && pacman.getStatusTime() == 0) {
                    if (currentChar == 0) {
                        setGameMode(new GameOverMode("You lost !!"));
                    }
                    else {
                        setGameMode(new GameOverMode("Congratulations !"));
                    }
                }
            }
        }
    }    
    
    private int lastEpoch = -1;
    private long beginEpoch = 0;
    public void render(long time) {
        if (state.getEpoch() != lastEpoch) {
            lastEpoch = state.getEpoch();
            beginEpoch = System.nanoTime();
        }
        renderer.render(beginEpoch,time,beginEpoch+state.getEpochDuration(),state);
    }

}
