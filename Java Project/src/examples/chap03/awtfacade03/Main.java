/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap03.awtfacade03;

public class Main {

    private GUIFacade gui;

    private Layer levelLayer;

    private Layer charsLayer;

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
    
    void setGUI(GUIFacade gui) {
        this.gui = gui;
    }

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
        
        gui.createWindow(levelWidth * levelLayer.getTileWidth(), levelHeight * levelLayer.getTileHeight(), "GUI Facade");
    }

    public void run() {
        int fps = 60;
        long nanoPerFrame = (long) (1000000000.0 / fps);
        long lastTime = 0;
        while (!gui.isClosingRequested()) {
            long nowTime = System.nanoTime();
            if ((nowTime - lastTime) < nanoPerFrame) {
                continue;
            }
            lastTime = nowTime;
            render();
            long elapsed = System.nanoTime() - lastTime;
            long milliSleep = (nanoPerFrame - elapsed) / 1000000;
            if (milliSleep > 0) {
                try {
                    Thread.sleep(milliSleep);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        gui.dispose();
    }

    public void render() {
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

    public static void main(String args[]) {
        Main pacman = new Main();
        pacman.setGUI(new AWTGUIFacade());
        pacman.init();
        pacman.run();
    }
}
