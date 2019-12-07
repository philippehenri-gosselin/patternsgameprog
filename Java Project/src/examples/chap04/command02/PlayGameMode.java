/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.command02;

import examples.chap04.command02.gui.Keyboard;
import examples.chap04.command02.state.Characters;
import examples.chap04.command02.state.Direction;
import examples.chap04.command02.state.State;
import examples.chap04.command02.state.World;
import examples.chap04.command02.render.Renderer;
import examples.chap04.command02.rules.DirectionCommand;
import examples.chap04.command02.rules.InitCommand;
import examples.chap04.command02.rules.MoveCommand;
import examples.chap04.command02.rules.Rules;
import java.awt.event.KeyEvent;

public class PlayGameMode extends GameMode {

    private State state;
    
    private Renderer renderer;
    
    private Rules rules;
    
    private int currentChar = 0; 
   
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
            
            Characters chars = state.getChars();
            for (int charIndex=0;charIndex<chars.size();charIndex++) {
                rules.addCommand(100+charIndex, new MoveCommand(charIndex));
            }
            
            rules.update();
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
