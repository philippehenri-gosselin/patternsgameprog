/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap05.connect4.ai;

import examples.chap05.connect4.Board;
import examples.chap05.connect4.Command;
import java.util.ArrayList;
import pacman.ai.tree.Node;

public class BoardNode extends Node {
    
    private final Board board;
    
    private final int player;
    
    private final int currentPlayer;
    
    private final Command command;

    public BoardNode(Board board,int player) {
        super(null);
        this.board = board;
        this.player = player;
        this.currentPlayer = board.getCurrentPlayer();
        this.command = null;
    }
    
    public BoardNode(Node parent,Command command) {
        super(parent);
        this.board = ((BoardNode)parent).board;
        this.player = ((BoardNode)parent).player;
        this.currentPlayer = board.getCurrentPlayer();
        this.command = command;
    }

    Command getCommand() {
        return command;
    }
    
    @Override
    public void createChildren() {
        if (board.getWinner() != 0)
            throw new RuntimeException("Internal error");
        
        ArrayList<Node> list = new ArrayList();
        for (int j=0;j<7;j++) {
            if (board.canPlay(j)) {
                list.add(new BoardNode(this,new Command(j)));
            }
        }
        children = list.toArray(new Node[0]);
    }

    @Override
    public void updateState() {
        if (command == null) {
            return;
        }
        if (board.getCurrentPlayer() != currentPlayer) {
            throw new RuntimeException("Internal error");
        }
        
        command.execute(board);
        if (board.isWinner(player)) {
            setValue(42 - depth); 
        }
        else if (board.isWinner(-player)) {
            setValue(depth - 42); 
        }
        else if (depth >= 7) {
            setValue(0);
        }
        else if (board.isOver()) {
            setValue(0);
        }
    }

    @Override
    public void rollbackState() {
        if (command == null) {
            return;
        }
        command.rollback(board);
        if (board.getCurrentPlayer() != currentPlayer) {
            throw new RuntimeException("Internal error");
        }
        children = null;
    }

}
