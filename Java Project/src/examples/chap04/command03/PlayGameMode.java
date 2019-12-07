/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.command03;

import examples.chap04.command03.gui.Keyboard;
import examples.chap04.command03.state.Characters;
import examples.chap04.command03.state.Direction;
import examples.chap04.command03.state.State;
import examples.chap04.command03.render.Renderer;
import examples.chap04.command03.rules.CollisionsCommand;
import examples.chap04.command03.rules.DirectionCommand;
import examples.chap04.command03.rules.GumsCommand;
import examples.chap04.command03.rules.InitCommand;
import examples.chap04.command03.rules.MoveCommand;
import examples.chap04.command03.rules.ResurrectionCommand;
import examples.chap04.command03.rules.Rules;
import examples.chap04.command03.rules.UpdateStatusCommand;
import examples.chap04.command03.state.Pacman;
import examples.chap04.command03.state.PacmanStatus;
import java.awt.event.KeyEvent;

public class PlayGameMode extends GameMode {

    private State state;
    
    private Renderer renderer;
    
    private Rules rules;
    
    private int currentChar = 0; 
    
    public PlayGameMode() {
    
    }
   
    public void init() 
    {
        state = new State();
        
        renderer = new Renderer(gui);
        state.registerObserver(renderer);
        
        rules = new Rules(state);
        rules.addCommand(0,new InitCommand());
        rules.update();
    }
    
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
            case KeyEvent.VK_BACK_SPACE:
                rules.addCommand(0,new InitCommand());
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
    
    private long lastUpdate;
    public void update() {
        long now = System.nanoTime();

        if ( (now - lastUpdate) >= state.getEpochDuration()) 
        {
            lastUpdate = now;
            
            rules.addCommand(100, new MoveCommand(0));
            rules.addCommand(200, new UpdateStatusCommand(0));
            rules.addCommand(300, new GumsCommand());
            rules.addCommand(400, new CollisionsCommand());

            Characters chars = state.getChars();
            for (int charIndex=1;charIndex<chars.size();charIndex++) {
                rules.addCommand(500+charIndex, new MoveCommand(charIndex));
                rules.addCommand(600+charIndex, new UpdateStatusCommand(charIndex));
                rules.addCommand(700+charIndex, new ResurrectionCommand(charIndex));
            }
            rules.addCommand(800, new CollisionsCommand());
            
            rules.update();
            
            if (state.getGumCount() == 0) {
                setGameMode(new GameOverMode("Victory !"));
            }
            Pacman pacman = chars.getPacman();
            if (pacman != null && pacman.getStatus() == PacmanStatus.DEAD && pacman.getStatusTime() == 0) {
                setGameMode(new GameOverMode("You lost !!"));
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
