/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.draw04.render;

import examples.chap04.draw04.gui.GUIFacade;
import examples.chap04.draw04.gui.Layer;
import examples.chap04.draw04.state.Characters;
import examples.chap04.draw04.state.Direction;
import examples.chap04.draw04.state.MobileElement;
import examples.chap04.draw04.state.State;
import examples.chap04.draw04.state.StateObserver;
import examples.chap04.draw04.state.StaticElement;
import examples.chap04.draw04.state.World;
import java.util.HashMap;
import java.util.Map;

public class Renderer implements StateObserver {
    
    private GUIFacade gui;

    private Layer levelLayer;

    private TileSet levelTileSet;

    private Layer charsLayer;

    private TileSet charsTileSet;

    private HashMap<LayerElement, Animation> animations = new HashMap();

    public Renderer(GUIFacade gui) {
        this.gui = gui;
        Object o;
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
        
        state.registerObserver(this);
        
        gui.createWindow(world.getWidth() * levelLayer.getTileWidth(), world.getHeight() * levelLayer.getTileHeight(), "State and GUI Synchronization");
    }

    public void render(long time,State state) 
    {
        for (Map.Entry<LayerElement, Animation> entry : animations.entrySet()) {
            LayerElement element = entry.getKey();
            Animation animation = entry.getValue();
            animation.render(element,time);
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
    
    public void worldElementChanged(State state, int x, int y) {
        World world = state.getWorld();
        int index = x + y * world.getWidth();
        StaticElement se = world.get(x, y, Direction.NONE);
        Tile tile = levelTileSet.getTile(se);
        if (tile instanceof StaticTile) {
            LayerElement element = new LayerElement(levelLayer,index);
            animations.remove(element);
            levelLayer.setSpriteTexture(index, tile.getX(0),tile.getY(0));
        }
        else {
            LayerElement element = new LayerElement(levelLayer,index);
            Animation animation = animations.get(element);
            if (animation == null) {
                animation = new Animation();
                animations.put(element,animation);
            }
            animation.setTile(tile);
        }
    } 
    
    public void characterChanged(State state, int charIndex) {
        Characters chars = state.getChars();
        MobileElement me = chars.get(charIndex);
        
        Tile tile = charsTileSet.getTile(me);
        LayerElement element = new LayerElement(charsLayer,charIndex);
        Animation animation = animations.get(element);
        if (animation == null) {
            animation = new Animation();
            animations.put(element,animation);
        }
        animation.setTile(tile);
        
        Direction d = me.getDirection();            
        int x = me.getX() * charsLayer.getTileWidth();
        int y = me.getY() * charsLayer.getTileHeight();
        if (d == Direction.EAST || d == Direction.WEST) {
            x += (charsTileSet.getTileWidth() * me.getPosition()) / (2*me.getSpeed());
        }
        else if (d == Direction.NORTH || d == Direction.SOUTH) {
            y += (charsTileSet.getTileHeight()* me.getPosition()) / (2*me.getSpeed());
        }
        charsLayer.setSpriteLocation(charIndex, x, y);
    }


}
