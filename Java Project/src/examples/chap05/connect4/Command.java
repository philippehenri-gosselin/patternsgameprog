/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap05.connect4;

public class Command {
    
    private final int column;
    
    private boolean played = false;

    public Command(int column) {
        this.column = column;
    }
    
    public int getColumn() {
        return column;
    }    
    
    public void execute(Board board) {
        if (!played && board.play(column)) {
            played = true;
        }
    }
    
    public void rollback(Board board) {
        if (played) {
            board.cancel(column);
            played = false;
        }
    }

}
