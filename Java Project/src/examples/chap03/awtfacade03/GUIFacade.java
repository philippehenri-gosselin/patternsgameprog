/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap03.awtfacade03;

public interface GUIFacade {

    public void createWindow(int width, int height, String title);

    public boolean isClosingRequested();

    public void dispose();

    public boolean beginPaint();

    public void endPaint();

    public void clearBackground();

    public Layer createLayer();

    public void drawLayer(Layer layer);
}
