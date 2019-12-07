/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.draw03.render;

import examples.chap04.draw03.gui.GUIFacade;
import examples.chap04.draw03.gui.Layer;
import examples.chap04.draw03.state.Characters;
import examples.chap04.draw03.state.Direction;
import examples.chap04.draw03.state.Element;
import examples.chap04.draw03.state.MobileElement;
import examples.chap04.draw03.state.State;
import examples.chap04.draw03.state.World;

public class Renderer {
    
    private GUIFacade gui;

    private Layer levelLayer;

    private TileSet levelTileSet;

    private Layer charsLayer;

    private TileSet charsTileSet;

    public Renderer(GUIFacade gui) {
        this.gui = gui;
    }
    
    public void init(State state) {
        Characters chars = state.getChars();
        charsTileSet = new CharsTileSet();
        charsLayer = gui.createLayer();
        charsLayer.setTileSize(charsTileSet.getTileWidth(), charsTileSet.getTileHeight());
        charsLayer.setTexture(charsTileSet.getImageFile());
        charsLayer.setSpriteCount(chars.size());
        
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
            }
        }        
        gui.createWindow(world.getWidth() * levelLayer.getTileWidth(), world.getHeight() * levelLayer.getTileHeight(), "State and GUI Synchronization");
    }

    public void render(long time,State state) 
    {
        World world = state.getWorld(); 
        for (int j = 0; j < world.getHeight(); j++) {
            for (int i = 0; i < world.getWidth(); i++) {
                int index = i + j * world.getWidth();
                Element element = world.get(i, j, Direction.NONE);
                Tile tile = levelTileSet.getTile(element);
                levelLayer.setSpriteTexture(index, tile.getX(time),tile.getY(time));
            }
        }        

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
            charsLayer.setSpriteTexture(index, tile.getX(time), tile.getY(time));
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
