/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.threads01.gui;

public interface Mouse {

    public boolean isButtonPressed(int button);

    public int getX();

    public int getY();
}
