/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.features03.render;

import examples.chap04.features03.gui.GUIFacade;
import examples.chap04.features03.gui.Layer;
import examples.chap04.features03.state.Characters;
import examples.chap04.features03.state.Direction;
import examples.chap04.features03.state.MobileElement;
import examples.chap04.features03.state.State;
import examples.chap04.features03.state.StateObserver;
import examples.chap04.features03.state.StaticElement;
import examples.chap04.features03.state.World;
import java.awt.Point;
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
    }
 
    public void stateChanged(State state) {
        Characters chars = state.getChars();
        charsTileSet = new CharsTileSet();
        charsLayer = gui.createLayer();
        charsLayer.setTileSize(charsTileSet.getTileWidth(), charsTileSet.getTileHeight());
        charsLayer.setTexture(charsTileSet.getImageFile());
        charsLayer.setSpriteCount(chars.size());
        for (int index=0;index<chars.size();index++) {
            characterChanged(state, index);
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
                worldElementChanged(state, i, j);
            }
        }        
        
        gui.createWindow(world.getWidth() * levelLayer.getTileWidth(), world.getHeight() * levelLayer.getTileHeight(), "Moteur de règles");
    }

    public void render(long begin,long time,long end,State state) 
    {
        for (Map.Entry<LayerElement, Animation> entry : animations.entrySet()) {
            LayerElement element = entry.getKey();
            Animation animation = entry.getValue();
            animation.render(element,begin,time,end);
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
        LayerElement element = new LayerElement(levelLayer,index);
        if (tile instanceof AnimatedTile) {
            Animation animation = animations.get(element);
            if (animation == null) {
                animation = new Animation();
                animations.put(element,animation);
            }
            animation.setTile(tile);
        }
        else {
            animations.remove(element);
            levelLayer.setSpriteTexture(index, tile.getX(0),tile.getY(0));
        }
    }
    
    public void characterChanged(State state, int charIndex) {
        Characters chars = state.getChars();
        MobileElement me = chars.get(charIndex);
        
        Tile tile = charsTileSet.getTile(me);
        LayerElement element = new LayerElement(charsLayer,charIndex);
        MobileAnimation animation = (MobileAnimation)animations.get(element);
        if (animation == null) {
            animation = new MobileAnimation();
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
        animation.setDest(new Point(x,y));

        if (d == Direction.NONE) {
            animation.setStart(new Point(x,y));
        }
        else {
            Point displayLocation = charsLayer.getSpriteLocation(charIndex);        
            World world = state.getWorld();
            int worldWidth = world.getWidth() * charsTileSet.getTileWidth();
            if (Math.abs(displayLocation.x-x) > charsTileSet.getTileWidth() ) {
                if (displayLocation.x < 0) displayLocation.x += worldWidth;
                else displayLocation.x -= worldWidth;
            }
            int worldHeight = world.getHeight() * charsTileSet.getTileHeight();
            if (Math.abs(displayLocation.y-y) > charsTileSet.getTileHeight() ) {
                if (displayLocation.y < 0) displayLocation.y += worldHeight;
                else displayLocation.y -= worldHeight;
            }
            animation.setStart(displayLocation);
        }

    }

}
