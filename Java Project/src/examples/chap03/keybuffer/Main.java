/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap03.keybuffer;

import java.util.List;

public class Main {

    private GUIFacade gui;

    private boolean rightArrow;
    private boolean upArrow;
    private boolean enter;
    
    private boolean combo1;
    private boolean combo2;
    private boolean combo3;
    
    public void handleInputs() {
        Keyboard keyboard = gui.getKeyboard();
        rightArrow = keyboard.isKeyPressed(java.awt.event.KeyEvent.VK_RIGHT);
        upArrow = keyboard.isKeyPressed(java.awt.event.KeyEvent.VK_UP);
        enter = keyboard.isKeyPressed(java.awt.event.KeyEvent.VK_ENTER);
        
        List<KeyEvent> keyEvents = keyboard.getKeyEvents();
        for (int i=0;i<keyEvents.size();i++) {
            if (i+1 < keyEvents.size()
             && keyEvents.get(i+0).getKeyCode() == java.awt.event.KeyEvent.VK_RIGHT
             && keyEvents.get(i+0).getType() == KeyEventType.KEY_PRESSED
             && keyEvents.get(i+1).getKeyCode() == java.awt.event.KeyEvent.VK_RIGHT
             && keyEvents.get(i+1).getType() == KeyEventType.KEY_RELEASED
                    ) {
                combo1 = !combo1;
                keyboard.clearKeyEvents();
                break;
            }
            if (i+3 < keyEvents.size()
             && keyEvents.get(i+0).getKeyCode() == java.awt.event.KeyEvent.VK_UP
             && keyEvents.get(i+0).getType() == KeyEventType.KEY_PRESSED
             && keyEvents.get(i+1).getKeyCode() == java.awt.event.KeyEvent.VK_UP
             && keyEvents.get(i+1).getType() == KeyEventType.KEY_RELEASED
             && keyEvents.get(i+2).getKeyCode() == java.awt.event.KeyEvent.VK_ENTER
             && keyEvents.get(i+2).getType() == KeyEventType.KEY_PRESSED
             && keyEvents.get(i+3).getKeyCode() == java.awt.event.KeyEvent.VK_ENTER
             && keyEvents.get(i+3).getType() == KeyEventType.KEY_RELEASED
                    ) {
                combo2 = !combo2;
                keyboard.clearKeyEvents();
                break;
            }
            if (i+2 < keyEvents.size()
             && keyEvents.get(i+0).getKeyCode() == java.awt.event.KeyEvent.VK_RIGHT
             && keyEvents.get(i+0).getType() == KeyEventType.KEY_PRESSED
             && keyEvents.get(i+1).getKeyCode() == java.awt.event.KeyEvent.VK_UP
             && keyEvents.get(i+1).getType() == KeyEventType.KEY_PRESSED
             && keyEvents.get(i+2).getKeyCode() == java.awt.event.KeyEvent.VK_ENTER
             && keyEvents.get(i+2).getType() == KeyEventType.KEY_PRESSED
                    ) {
                combo3 = !combo3;
                keyboard.clearKeyEvents();
                break;
            }
        }
    }
    
    public void render() {
        if (!gui.beginPaint())
            return;
        try {
            gui.clearBackground();
            
            gui.drawString("Right arrow: "+(rightArrow?"pressed":"released"), 0, 1*20);
            gui.drawString("Up arrow: "+(upArrow?"pressed":"released"), 0, 2*20);
            gui.drawString("Enter: "+(enter?"pressed":"released"), 0, 3*20);
            
            gui.drawString("Right arrow pressed, released: "+(combo1?"enabled":"disabled"), 0, 5*20);
            gui.drawString("Up arrow pressed, released, Enter pressed, released: "+(combo2?"enabled":"disabled"), 0, 6*20);
            gui.drawString("Right arrow pressed, Up arrow pressed, Enter pressed: "+(combo3?"enabled":"disabled"), 0, 7*20);
            
            int y = 9*20;
            for (KeyEvent event : gui.getKeyboard().getKeyEvents()) {
                String type = event.getType()==KeyEventType.KEY_PRESSED?"pressed":"released";
                if (event.getKeyCode() == java.awt.event.KeyEvent.VK_RIGHT) {
                    gui.drawString("Right arrow "+type, 0, y);
                    y += 20;
                }
                else if (event.getKeyCode() == java.awt.event.KeyEvent.VK_UP) {
                    gui.drawString("Up arrow "+type, 0, y);
                    y += 20;
                }
                else if (event.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    gui.drawString("Enter "+type, 0, y);
                    y += 20;
                }
                else {
                    gui.drawString("Key "+event.getKeyCode()+" "+type, 0, y);
                    y += 20;
                }
            }
            
            
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
            
            handleInputs();
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
        pacman.run();
    }
}
