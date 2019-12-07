/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap03.gamemodes03;

import examples.chap03.gamemodes03.gui.Image;
import examples.chap03.gamemodes03.gui.Keyboard;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class MainMenuGameMode extends GameMode {

    private Image titleImage;
    
    private Image selectImage;
    
    private List<String> items = new ArrayList();
    
    private int selectedItem;    

    private final static int windowWidth = 640;

    private final static int windowHeight = 480;

   public MainMenuGameMode() {
        items.add("Play Pacman");
        items.add("Play a Ghost");
        items.add("Exit");
    }
    
    public void init() {
        titleImage = gui.createImage("pacman_title.png");
        selectImage = gui.createImage("select.png");
        
        gui.createWindow(windowWidth, windowHeight, "Pacman");
    }

    public void handleInputs() {
        Keyboard keyboard = gui.getKeyboard();
        switch(keyboard.getLastPressedKey()) {
            case KeyEvent.VK_ESCAPE:
                keyboard.consumeLastPressedKey();
                gui.setClosingRequested(true);
                return;
            case KeyEvent.VK_UP:
                keyboard.consumeLastPressedKey();
                if (selectedItem > 0) {
                    selectedItem --;
                }
                return;
            case KeyEvent.VK_DOWN:
                keyboard.consumeLastPressedKey();
                if (selectedItem < items.size()-1) {
                    selectedItem ++;
                }
                return;
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_ENTER:
                keyboard.consumeLastPressedKey();
                switch(selectedItem) {
                    case 0: 
                        setGameMode(new PlayGameMode());
                        break;
                    case 1:
                        JOptionPane.showMessageDialog(null, "Under construction !","Error",JOptionPane.ERROR_MESSAGE);
                        break;
                    case 2:
                        gui.setClosingRequested(true);
                        break;
                }
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
            gui.drawImage(titleImage, 
                (windowWidth-titleImage.getWidth())/2, 
                50
            );
            gui.setColor(Color.white);
            gui.setTextSize(selectImage.getHeight());
            
            Dimension menuSize = paintMenu(0,0,true);
            paintMenu(
                (windowWidth-menuSize.width)/2, 
                (windowHeight-menuSize.height)/2, 
                false
            );
            
        } finally {
            gui.endPaint();
        }
    }

    
    private Dimension paintMenu(int x,int y,boolean computeSize) {
        int menuWidth = 0;
        int menuHeight = 0;
        for (int i=0;i<items.size();i++) {
            String text = items.get(i);
            Dimension textSize = gui.getTextMetrics(text);
            if (!computeSize) {
                gui.drawText(text, x, y, textSize.width, textSize.height);
                if (i == selectedItem) {
                    gui.drawImage(selectImage, x-selectImage.getWidth(), y);
                }
            }
            y += textSize.height;
            menuHeight += textSize.height;
            if (textSize.width > menuWidth) {
                menuWidth = textSize.width;
            }
        }
        return new Dimension(menuWidth,menuHeight);
    }
}
