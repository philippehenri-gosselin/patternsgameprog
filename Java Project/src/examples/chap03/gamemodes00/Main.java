/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap03.gamemodes00;

import examples.chap03.gamemodes00.gui.GUIFacade;
import examples.chap03.gamemodes00.gui.awt.AWTGUIFacade;
import javax.swing.JOptionPane;

public class Main {

    private GUIFacade gui;
    
    private GameMode mode;

    
    public void setGUI(GUIFacade gui) {
        this.gui = gui;
    }
    
    public void init() {
        try {
            mode = new PlayGameMode();
            mode.setGUI(gui);
            mode.init();
        }
        catch(Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
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

            mode.handleInputs();
            mode.update();
            mode.render();            

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
    
    public static void main(String args[]) {
        Main pacman = new Main();
        pacman.setGUI(new AWTGUIFacade());
        pacman.init();
        pacman.run();
    }



}
