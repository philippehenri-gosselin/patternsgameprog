/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap03.facadeimage;

import java.util.List;

public class Main {

    private GUIFacade gui;

    private Image titleImage;
            
    public void init() {
        titleImage = gui.createImage("pacman_title.png");
    }
        
    public void render() {
        if (!gui.beginPaint())
            return;
        try {
            gui.clearBackground();
                        
            gui.drawImage(titleImage, 
                (800-titleImage.getWidth())/2, 
                (600-titleImage.getHeight())/2
            );
            
        } finally {
            gui.endPaint();
        }
    }

    public void run() {
        gui.createWindow("GUI Facade");
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

    void setGUI(GUIFacade gui) {
        this.gui = gui;
    }

    public static void main(String args[]) {
        Main pacman = new Main();
        pacman.setGUI(new AWTGUIFacade());
        pacman.init();
        pacman.run();
    }
}
