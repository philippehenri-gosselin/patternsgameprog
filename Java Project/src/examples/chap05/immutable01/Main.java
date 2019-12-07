/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.immutable01;

import examples.chap05.immutable01.gui.GUIFacade;
import examples.chap05.immutable01.gui.awt.AWTGUIFacade;
import java.util.LinkedList;
import javax.swing.JOptionPane;

public class Main {

    private GUIFacade gui;
    
    private GameMode currentMode;

    private LinkedList<GameMode> gameModes = new LinkedList();
  
    private GameBuilder gameBuilder = new GameBuilder();
    
    public GameBuilder getGameBuilder() {
        return gameBuilder;
    }
    
    public void setGUI(GUIFacade gui) {
        this.gui = gui;
    }
    
    public synchronized void setGameMode(GameMode mode) {
        try {
            gameModes.push(mode);
            mode.setParent(this);
            mode.setGUI(gui);
            mode.init();
            this.currentMode = mode;
        }
        catch(Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }    
    
    public synchronized void setPreviousGameMode() {
        if (!gameModes.isEmpty()) {
            gameModes.pop();
        }
        if (gameModes.isEmpty()) {
            gui.setClosingRequested(true);
        }
        else {
            setGameMode(gameModes.pop());
        }
    }      
    
    public void clearGameModes() {
        gameModes.clear();
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
            
            synchronized(this) {
                currentMode.handleInputs();
                currentMode.render(nowTime);            
                currentMode.update();
            }
            
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
        GameBuilder builder = new GameBuilder();
        builder.setCharIndex(0);
        builder.setDifficulty(1);
        pacman.setGameMode(builder.createGame());
        pacman.run();
    }

}
