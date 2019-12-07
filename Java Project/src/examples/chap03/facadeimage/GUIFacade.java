/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap03.facadeimage;

public interface GUIFacade {

    public void createWindow(String title);

    public boolean beginPaint();

    public void endPaint();

    public boolean isClosingRequested();

    public void dispose();

    public void clearBackground();

    public Image createImage(String fileName);
    
    public void drawImage(Image image, int x, int y);
}
