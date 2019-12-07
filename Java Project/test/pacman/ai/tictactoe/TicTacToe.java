/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package pacman.ai.tictactoe;

import java.util.Arrays;

public class TicTacToe implements Cloneable {
    public int epoch;
    public int[][] cells = {{0,0,0},{0,0,0},{0,0,0}};

    public boolean hasWon(int player) {
        for (int j=0;j<3;j++) {
            if (cells[0][j] == player
             && cells[1][j] == player
             && cells[2][j] == player) {
                return true;
            }
            if (cells[j][0] == player
             && cells[j][1] == player
             && cells[j][2] == player) {
                return true;
            }
        }
        if (cells[0][0] == player
         && cells[1][1] == player
         && cells[2][2] == player) {
            return true;
        }
        if (cells[0][2] == player
         && cells[1][1] == player
         && cells[2][0] == player) {
            return true;
        }
        return false;
    }

    public void show() {
        System.out.println("Epoch "+epoch);
        for (int y=0;y<3;y++) {
            for (int x=0;x<3;x++) {
                if (x > 0)
                    System.out.print("|");
                if (cells[x][y]>0)
                    System.out.print("X");
                else if (cells[x][y]<0)
                    System.out.print("0");
                else
                    System.out.print(" ");
            }
            System.out.println();
        }
    }
    
    @Override
    public TicTacToe clone() {
        TicTacToe other = new TicTacToe();
        other.epoch = epoch;
        other.cells = new int[3][3];
        for (int y=0;y<3;y++) {
            for (int x=0;x<3;x++) {
                other.cells[x][y] = cells[x][y];
            }
        }
        return other;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TicTacToe other = (TicTacToe) obj;
        if (this.epoch != other.epoch) {
            return false;
        }
        if (!Arrays.deepEquals(this.cells, other.cells)) {
            return false;
        }
        return true;
    }
    
    

}
