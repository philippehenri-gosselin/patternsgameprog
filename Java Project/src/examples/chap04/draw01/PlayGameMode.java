/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.draw01;

import examples.chap04.draw01.render.TileSet;
import examples.chap04.draw01.gui.Keyboard;
import examples.chap04.draw01.gui.Layer;
import examples.chap04.draw01.menu.MainMenu;
import examples.chap04.draw01.state.Direction;
import examples.chap04.draw01.state.Element;
import examples.chap04.draw01.state.ElementFactory;
import examples.chap04.draw01.state.State;
import examples.chap04.draw01.state.World;
import examples.chap04.draw01.render.GridTileSet;
import examples.chap04.draw01.render.Tile;
import java.awt.event.KeyEvent;

public class PlayGameMode extends GameMode {

    private Layer levelLayer;

    private TileSet levelTileSet;

    private State state;
    
    static final int[][] level = new int[][] {
        { 15,11,11,11,11,11,11,11,16 },
        { 12,5,3,3,3,3,3,3,12 },
        { 12,3,15,11,11,11,16,3,12 },
        { 14,3,13,11,11,11,14,3,13 },
        { 3,3,3,3,3,3,3,3,3 },
        { 11,11,11,11,11,11,11,11,11 }
    };     
    
    public PlayGameMode() {
        World world = new World(9,6);
        world.setFactory(ElementFactory.getDefault());
        world.init(level);
        
        state = new State();
        state.setWorld(world);
    }
   
    public void init() 
    {
        levelTileSet = new GridTileSet();
        
        World world = state.getWorld();        
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
        Keyboard keyboard = gui.getKeyboard();
        switch(keyboard.getLastPressedKey()) {
            case KeyEvent.VK_ESCAPE:
                keyboard.consumeLastPressedKey();
                setPreviousGameMode();
                return;
        }
    }
    
    public void update() {
    }    
    
    public void render() {
        if (!gui.beginPaint())
            return;
        try {
            gui.clearBackground();
            gui.drawLayer(levelLayer);
        } finally {
            gui.endPaint();
        }
    }

}
