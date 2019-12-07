/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.draw02;

import examples.chap04.draw02.tile.TileSet;
import examples.chap04.draw02.gui.Keyboard;
import examples.chap04.draw02.gui.Layer;
import examples.chap04.draw02.state.Characters;
import examples.chap04.draw02.state.Direction;
import examples.chap04.draw02.state.Element;
import examples.chap04.draw02.state.ElementFactory;
import examples.chap04.draw02.state.MobileElement;
import examples.chap04.draw02.state.State;
import examples.chap04.draw02.state.World;
import examples.chap04.draw02.tile.CharsTileSet;
import examples.chap04.draw02.tile.GridTileSet;
import examples.chap04.draw02.tile.Tile;
import java.awt.event.KeyEvent;

public class PlayGameMode extends GameMode {

    private Layer levelLayer;

    private TileSet levelTileSet;
    
    private Layer charsLayer;

    private TileSet charsTileSet;

    private State state;
    
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
        Characters chars = state.getChars();
        charsTileSet = new CharsTileSet();
        charsLayer = gui.createLayer();
        charsLayer.setTileSize(charsTileSet.getTileWidth(), charsTileSet.getTileHeight());
        charsLayer.setTexture(charsTileSet.getImageFile());
        charsLayer.setSpriteCount(chars.size());
        for (int index=0;index<chars.size();index++) {
            MobileElement me = chars.get(index);
            charsLayer.setSpriteLocation(index, me.getX() * charsLayer.getTileWidth(), me.getY() * charsLayer.getTileHeight());
            Tile tile = charsTileSet.getTile(me);
            charsLayer.setSpriteTexture(index, tile.getX(), tile.getY());
        }
        
        World world = state.getWorld();        
        levelTileSet = new GridTileSet();
        levelLayer = gui.createLayer();
        levelLayer.setTileSize(levelTileSet.getTileWidth(), levelTileSet.getTileHeight());
        levelLayer.setTexture(levelTileSet.getImageFile());
        levelLayer.setSpriteCount(world.getWidth() * world.getHeight());
        for (int j = 0; j < world.getHeight(); j++) {
            for (int i = 0; i < world.getWidth(); i++) {
                int index = i + j * world.getWidth();
                levelLayer.setSpriteLocation(index, i * levelLayer.getTileWidth(), j * levelLayer.getTileHeight());
                Element element = world.get(i, j, Direction.NONE);
                Tile tile = levelTileSet.getTile(element);
                levelLayer.setSpriteTexture(index, tile.getX(),tile.getY());
            }
        }        
        
        gui.createWindow(world.getWidth() * levelLayer.getTileWidth(), world.getHeight() * levelLayer.getTileHeight(), "State and GUI Synchronization");
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

        if ( (now - lastUpdate) >= 1000000000/12) 
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
            }
        }
    }    
    
    public void render() {
            
        Characters chars = state.getChars();
        for (int index=0;index<chars.size();index++) {
            MobileElement me = chars.get(index);
            int x = me.getX() * charsLayer.getTileWidth();
            int y = me.getY() * charsLayer.getTileHeight();
            Direction d = me.getDirection();
            if (d == Direction.EAST || d == Direction.WEST) {
                x += (charsTileSet.getTileWidth() * me.getPosition()) / (2*me.getSpeed());
            }
            else if (d == Direction.NORTH || d == Direction.SOUTH) {
                y += (charsTileSet.getTileHeight()* me.getPosition()) / (2*me.getSpeed());
            }
            charsLayer.setSpriteLocation(index, x, y);
            Tile tile = charsTileSet.getTile(me);
            charsLayer.setSpriteTexture(index, tile.getX(), tile.getY());
        }

        if (!gui.beginPaint())
            return;
        try {
            gui.clearBackground();
            gui.drawLayer(levelLayer);
            gui.drawLayer(charsLayer);
        } finally {
            gui.endPaint();
        }
    }

}
