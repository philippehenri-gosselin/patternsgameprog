/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package pacman.ai.tictactoe;

import pacman.ai.tree.Node;
import java.util.ArrayList;

public class TicTacToeNode extends Node 
{
    private final TicTacToe state;
    private final int epoch;
    private final int x;
    private final int y;
    private final int player;
    private final int currentPlayer;

    public static int stateUpdateCount = 0;

    public TicTacToeNode(TicTacToe state,int player) {
        super(null);
        this.state = state;
        this.epoch = state.epoch;
        this.player = player;
        this.currentPlayer = -player;
        this.x = -1;
        this.y = -1;
    }

    public TicTacToeNode(Node parent,TicTacToe state,int player,int x,int y,int currentPlayer) {
        super(parent);
        this.state = state;
        this.epoch = state.epoch+1;
        this.x = x;
        this.y = y;
        this.player = player;
        this.currentPlayer = currentPlayer;
    }

    public TicTacToe getState() {
        return state;
    }

    public int getPlayer() {
        return currentPlayer;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int[][] getScores() {
        int[][] cells = new int[][]{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        for (int i = 0; i < getChildCount(); i++) {
            TicTacToeNode node = (TicTacToeNode) getChild(i);
            cells[node.getX()][node.getY()] = node.getValue();
        }
        return cells;
    }

    public void showScores() {
        int[][] cells = getScores();
        System.out.println("Scores");
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                System.out.print(cells[x][y] + "|");
            }
            System.out.println();
        }
    }

    @Override
    public void createChildren() {
        if (state.epoch != epoch)
            throw new RuntimeException("Internal error");
        ArrayList<Node> list = new ArrayList();
        for (int j=0;j<3;j++) {
            for (int i=0;i<3;i++) {
                if (state.cells[i][j] == 0) {
                    list.add(new TicTacToeNode(this,state,player,i,j,-currentPlayer));
                }
            }
        }
        children = list.toArray(new Node[0]);
    }

    @Override
    public void updateState() {
        if (state.cells[x][y] != 0)
            throw new RuntimeException("Internal error");
        if (state.epoch != epoch-1)
            throw new RuntimeException("Internal error");
        state.cells[x][y] = currentPlayer;
        state.epoch ++;
        stateUpdateCount ++;

        if (state.hasWon(currentPlayer)) {
            if (player == currentPlayer) {
                setValue(10-state.epoch);
            }
            else {
                setValue(-state.epoch);
            }
        }
        else if (state.epoch == 9) {
            setValue(0);
        }
    }

    @Override
    public void rollbackState() {
        if (state.cells[x][y] != currentPlayer)
            throw new RuntimeException("Internal error");
        if (state.epoch != epoch)
            throw new RuntimeException("Internal error");
        state.cells[x][y] = 0;
        state.epoch --;
        children = null;
    }
    
    @Override
    public Node createRoot() {
        return new TicTacToeNode(state.clone(),-currentPlayer);
    }
 
}
