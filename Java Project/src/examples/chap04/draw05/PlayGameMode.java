/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.draw05;

import examples.chap04.draw05.gui.Keyboard;
import examples.chap04.draw05.state.Characters;
import examples.chap04.draw05.state.Direction;
import examples.chap04.draw05.state.ElementFactory;
import examples.chap04.draw05.state.MobileElement;
import examples.chap04.draw05.state.State;
import examples.chap04.draw05.state.World;
import examples.chap04.draw05.render.Renderer;
import examples.chap04.draw05.state.Space;
import examples.chap04.draw05.state.SpaceTypeId;
import examples.chap04.draw05.state.StaticElement;
import java.awt.event.KeyEvent;

public class PlayGameMode extends GameMode {

    private State state;
    
    private Renderer renderer;
    
    private int currentChar = 0;
    
    static final int[][] level = new int[][] {
        { 15,11,11,11,11,11,11,11,16 },
        { 12,5,3,3,3,3,3,3,12 },
        { 12,3,15,11,11,11,16,3,12 },
        { 14,3,13,11,11,11,14,3,13 },
        { 21,3,3,3,25,24,22,23,3 },
        { 11,11,11,11,11,11,11,11,11 }
    };     
    
    public PlayGameMode() {
        World world = new World(9,6);
        world.setFactory(ElementFactory.getDefault());
        world.init(level);
        
        Characters chars = new Characters();
        chars.init(level);
        
        state = new State();
        state.setWorld(world);
        state.setChars(chars);        
    }
   
    public void init() 
    {
        renderer = new Renderer(gui);
        renderer.init(state);
        
        World world = state.getWorld(); 
        for (int y = 0; y < world.getHeight(); y++) {
            for (int x = 0; x < world.getWidth(); x++) {
                state.notifyWorldElementChanged(x, y);
            }
        }   
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
            state.incEpoch();
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
