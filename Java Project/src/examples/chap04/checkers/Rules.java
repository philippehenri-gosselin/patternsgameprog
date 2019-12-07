/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.checkers;

public class Rules {

    private Board board;

    private MoveCommand command;
    
    public Rules(Board board) {
        this.board = board;
    }
    
    public Board getBoard() {
        return board;
    }

    public void setCommand(MoveCommand command) {
        this.command = command;
    }

    public void update() {
        if (command != null) {
            command.execute(board);
            command = null;
        }
    }
}
