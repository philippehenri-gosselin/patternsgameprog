/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap03.gamemodes01;

import examples.chap03.gamemodes01.gui.Keyboard;
import examples.chap03.gamemodes01.gui.Layer;
import examples.chap03.gamemodes01.gui.Mouse;
import examples.chap03.gamemodes01.gui.awt.AWTGUIFacade;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class PlayGameMode extends GameMode {

    private Layer levelLayer;

    private Layer charsLayer;

    private Layer infoLayer;

    static final int levelWidth = 9;

    static final int levelHeight = 6;

    static final int[][] level = new int[][] {
        { 15,11,11,11,11,11,11,11,16 },
        { 12,5,3,3,3,3,3,3,12 },
        { 12,3,15,11,11,11,16,3,12 },
        { 14,3,13,11,11,11,14,3,13 },
        { 3,3,3,3,3,3,3,3,3 },
        { 11,11,11,11,11,11,11,11,11 }
    };   

    private Keyboard keyboard;

    private int pacmanX;

    private int pacmanY;

    private Mouse mouse;

    private int selectedTileX;

    private int selectedTileY;
    
    public void init() 
    {
        charsLayer = gui.createLayer();
        charsLayer.setTileSize(24, 24);
        charsLayer.setTexture("chars_tiles.png");
        charsLayer.setSpriteCount(1);
        charsLayer.setSpriteTexture(0, 6, 1);
        charsLayer.setSpriteLocation(0, 0 * charsLayer.getTileWidth(), 4 * charsLayer.getTileHeight());
        
        levelLayer = gui.createLayer();
        levelLayer.setTileSize(24, 24);
        levelLayer.setTexture("grid_tiles.png");
        levelLayer.setSpriteCount(levelWidth * levelHeight);

        infoLayer = gui.createLayer();
        infoLayer.setTileSize(24, 24);
        infoLayer.setTexture("grid_tiles.png");
        infoLayer.setSpriteCount(1);
        infoLayer.setSpriteTexture(0, 5, 0);
        infoLayer.setSpriteLocation(0, 0 * charsLayer.getTileWidth(), 0 * charsLayer.getTileHeight());

        gui.createWindow(levelWidth * levelLayer.getTileWidth(), levelHeight * levelLayer.getTileHeight(), "GUI Facade");
        keyboard = gui.getKeyboard();
        mouse = gui.getMouse();
    }
    
    public void handleInputs() {
        switch(keyboard.getLastPressedKey()) {
            case KeyEvent.VK_ESCAPE:
                keyboard.consumeLastPressedKey();
                setGameMode(new WelcomeGameMode());
                return;
        }
        
        if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)) {
            pacmanX ++;
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)) {
            pacmanX --;
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)) {
            pacmanY ++;
        }
        if (keyboard.isKeyPressed(KeyEvent.VK_UP)) {
            pacmanY --;
        }
        
        selectedTileX = mouse.getX() / levelLayer.getTileWidth();
        selectedTileY = mouse.getY() / levelLayer.getTileHeight();
        
        if (selectedTileX >= 0 && selectedTileX < levelWidth
         && selectedTileY >= 0 && selectedTileY < levelHeight) {
            if (mouse.isButtonPressed(MouseEvent.BUTTON1)) {
                level[selectedTileY][selectedTileX] = 2;
            }
            if (mouse.isButtonPressed(MouseEvent.BUTTON3)) {
                level[selectedTileY][selectedTileX] = 5;
            }
        }
    }
    
    private long lastUpdate1,lastUpdate2;
    public void update() {
        long now = System.nanoTime();
        if ( (now - lastUpdate1) >= 1000000000/4) {
            lastUpdate1 = now;
            for (int j=0;j<levelHeight;j++) {
                for (int i=0;i<levelWidth;i++) {    
                    int id = level[j][i];
                    if (id == 5) level[j][i] = 4;
                    else if (id == 4) level[j][i] = 5;
                }
            }
        }

        if ( (now - lastUpdate2) >= 1000000000/12) {
            lastUpdate2 = now;
            for (int j = 0; j < levelHeight; j++) {
                for (int i = 0; i < levelWidth; i++) {
                    int index = i + j * levelWidth;
                    levelLayer.setSpriteLocation(index, i * levelLayer.getTileWidth(), j * levelLayer.getTileHeight());
                    int tileIndex = level[j][i];
                    if (tileIndex < 0)
                        tileIndex = 0;
                    int tileX = (tileIndex - 1) % levelLayer.getTextureHeight();
                    int tileY = (tileIndex - 1) / levelLayer.getTextureHeight();
                    if (tileY >= levelLayer.getTextureHeight()) {
                        tileX = 0;
                        tileY = 0;
                    }
                    levelLayer.setSpriteTexture(index, tileX, tileY);
                }
            }        

            charsLayer.setSpriteLocation(0, pacmanX, pacmanY);
            infoLayer.setSpriteLocation(0, selectedTileX * charsLayer.getTileWidth(), selectedTileY * charsLayer.getTileHeight());
        }
    }    
    
    public void render() {
        if (!gui.beginPaint())
            return;
        try {
            gui.clearBackground();
            gui.drawLayer(levelLayer);
            gui.drawLayer(charsLayer);
            gui.drawLayer(infoLayer);
        } finally {
            gui.endPaint();
        }
    }


}
