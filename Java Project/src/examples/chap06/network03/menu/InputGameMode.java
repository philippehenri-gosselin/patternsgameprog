/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap06.network03.menu;

import examples.chap06.network03.GameMode;
import examples.chap06.network03.gui.Image;
import examples.chap06.network03.gui.Keyboard;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

public class InputGameMode extends GameMode {

    private Image titleImage;

    private Image caretImage;
    
    private String message;
    
    private String value;
    
    private Consumer<String> valueChanged;
    
    private int index;
    
    private final static int windowWidth = 640;

    private final static int windowHeight = 480;
    
    public InputGameMode(String message,String value,Consumer<String> valueChanged) {
        this.message = message;
        this.value = value;
        this.valueChanged = valueChanged;
        this.index = value.length();
    }
    
    public String getValue() {
        return value;
    }    
        
    public void init() {
        titleImage = gui.createImage("pacman_title.png");
        caretImage = gui.createImage("caret.png");     
        
        gui.createWindow(windowWidth, windowHeight, "Pacman");
    }

    public void handleInputs() {
        Keyboard keyboard = gui.getKeyboard();
        switch(keyboard.consumeLastPressedKey()) {
            case KeyEvent.VK_ESCAPE:
                setPreviousGameMode();
                return;
            case KeyEvent.VK_ENTER:
                valueChanged.accept(value);
                setPreviousGameMode();
                return;
            case KeyEvent.VK_RIGHT:
                if (index < value.length()) {
                    index ++;
                }
                return;
            case KeyEvent.VK_LEFT:
                if (index > 0) {
                    index --;
                }
                return;
            case KeyEvent.VK_BACK_SPACE:
                if (index > 0) {
                    value = value.substring(0,index-1)+value.substring(index);
                    index --;
                }
                return;
            case KeyEvent.VK_DELETE:
                if (index < value.length()) {
                    value = value.substring(0,index)+value.substring(index+1);
                }
                return;
            case KeyEvent.VK_HOME:
                index = 0;
                return;
            case KeyEvent.VK_END:
                index = value.length();
                return;
        }
        char c = keyboard.consumeLastTypedChar();
        if ((Character.isLetter(c) 
          || Character.isDigit(c)
          || c == '.')
         && value.length() < 100) {
            value = value.substring(0,index) + c + value.substring(index);
            index ++;
            return;
        }
    }

    public void update() {
    }

    public void render(long time) {
        if (!gui.beginPaint())
            return;
        try {
            gui.clearBackground();
            gui.drawImage(titleImage, 
                (windowWidth-titleImage.getWidth())/2, 
                50
            );
            gui.setColor(Color.white);
            gui.setTextSize(caretImage.getHeight());
            
            Dimension dimension = gui.getTextMetrics(message);
            gui.drawText(message, 
                (windowWidth-dimension.width)/2, (windowHeight-dimension.height)/2 - caretImage.getHeight()/2, 
                dimension.width, dimension.height);

            int y = (windowHeight-dimension.height)/2 + caretImage.getHeight()/2;
            String beginValue = value.substring(0,index);
            String endValue = value.substring(index);
            Dimension beginDimension = gui.getTextMetrics(beginValue);
            Dimension endDimension = gui.getTextMetrics(endValue);
            int x = (windowWidth-beginDimension.width-caretImage.getWidth()-endDimension.width)/2;
            gui.drawText(beginValue, x, y, 
                beginDimension.width, beginDimension.height);
            x += beginDimension.width;
            gui.drawImage(caretImage, x, y);
            x += caretImage.getWidth();
            gui.drawText(endValue, x, y, 
                endDimension.width, endDimension.height);
            
        } finally {
            gui.endPaint();
        }
    }

    

}
