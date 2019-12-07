/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.checkers;

public class MoveCommand {

    private int i0;

    private int j0;

    private int i1;

    private int j1;

    public MoveCommand(int i0, int j0, int i1, int j1) {
        this.i0 = i0;
        this.j0 = j0;
        this.i1 = i1;
        this.j1 = j1;
    }

    public void execute(Board board) {
        board.movePiece(i0, j0, i1, j1);
    }
}
