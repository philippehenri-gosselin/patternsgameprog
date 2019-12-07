/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.command01;

import examples.chap04.command01.gui.Keyboard;
import examples.chap04.command01.state.Characters;
import examples.chap04.command01.state.Direction;
import examples.chap04.command01.state.MobileElement;
import examples.chap04.command01.state.State;
import examples.chap04.command01.state.World;
import examples.chap04.command01.render.Renderer;
import examples.chap04.command01.rules.InitCommand;
import examples.chap04.command01.rules.Rules;
import examples.chap04.command01.state.Space;
import examples.chap04.command01.state.SpaceTypeId;
import examples.chap04.command01.state.StaticElement;
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
        rules.addCommand(new InitCommand());
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
                rules.addCommand(new InitCommand());
                break;
        }
        
        MobileElement me = chars.get(currentChar);
        int pos = me.getPosition();
        Direction d = me.getDirection();
        if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)) {
            if (pos == 0 || d == Direction.WEST) {
                me.setDirection(Direction.EAST);
            }
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)) {
            if (pos == 0 || d == Direction.EAST) {
                me.setDirection(Direction.WEST);
            }
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)) {
            if (pos == 0 || d == Direction.NORTH) {
                me.setDirection(Direction.SOUTH);
            }
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_UP)) {
            if (pos == 0 || d == Direction.SOUTH) {
                me.setDirection(Direction.NORTH);
            }
        }
        
    }
    
    private long lastUpdate;
    public void update() {
        long now = System.nanoTime();

        if ( (now - lastUpdate) >= state.getEpochDuration()) 
        {
            lastUpdate = now;
            
            World world = state.getWorld();
            Characters chars = state.getChars();
            for (int index=0;index<chars.size();index++) {
                MobileElement me = chars.get(index);
                int pos = me.getPosition();
                int x = me.getX();
                int y = me.getY();
                switch(me.getDirection()) {
                    case EAST:
                        pos ++;
                        if (pos == me.getSpeed()) {
                            pos = -me.getSpeed();
                            x ++;
                            if (x >= world.getWidth())
                                x = 0;
                        }
                        break;                    
                    case WEST:
                        pos --;
                        if (pos == -me.getSpeed()-1) {
                            pos = me.getSpeed()-1;
                            x --;
                            if (x < 0)
                                x = world.getWidth()-1;
                        }
                        break;                    
                    case SOUTH:
                        pos ++;
                        if (pos == me.getSpeed()) {
                            pos = -me.getSpeed();
                            y ++;
                            if (y >= world.getHeight())
                                y = 0;
                        }
                        break;                    
                    case NORTH:
                        pos --;
                        if (pos == -me.getSpeed()-1) {
                            pos = me.getSpeed()-1;
                            y --;
                            if (y < 0)
                                y = world.getHeight()-1;
                        }
                        break;                    
                }
                me.setPosition(pos);
                me.setX(x);
                me.setY(y);
                state.notifyCharacterChanged(index);
                
                if (index == 0 && pos == 0) {
                    StaticElement se = world.get(x, y, Direction.NONE);
                    if (se instanceof Space) {
                        Space space = (Space)se;
                        if (space.getSpaceTypeId() == SpaceTypeId.GUM) {
                            space.setSpaceTypeId(SpaceTypeId.EMPTY);
                            state.notifyWorldElementChanged(x, y);
                        }
                    }
                }
                
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
