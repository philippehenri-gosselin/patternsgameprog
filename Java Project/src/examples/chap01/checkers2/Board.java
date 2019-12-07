/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap01.checkers2;

public class Board {

    private int board[][];
    private int currentPlayer;

    public Board() {
        currentPlayer = 0;
        board = new int[8][8];
        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; i++) {
                board[i][j] = -1;
            }
        }
        board[0][0] = board[2][0] = board[4][0] = board[6][0] = 0;
        board[1][1] = board[3][1] = board[5][1] = board[7][1] = 0;
        board[0][6] = board[2][6] = board[4][6] = board[6][6] = 1;
        board[1][7] = board[3][7] = board[5][7] = board[7][7] = 1;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public int getPiece(int i, int j) {
        return board[i][j];
    }

    public boolean movePiece(int i0, int j0, int i, int j) {
        if (board[i][j] < 0 && ((i + j) % 2) == 0) {
            boolean doMove = false;
            if ((i == i0 + 1 && j == j0 + 1)
                    || (i == i0 + 1 && j == j0 - 1)
                    || (i == i0 - 1 && j == j0 + 1)
                    || (i == i0 - 1 && j == j0 - 1)) {
                doMove = true;
            } else if (i == i0 + 2 && j == j0 + 2 && board[i0 + 1][j0 + 1] == 1 - board[i0][j0]) {
                board[i0 + 1][j0 + 1] = -1;
                doMove = true;
            } else if (i == i0 - 2 && j == j0 + 2 && board[i0 - 1][j0 + 1] == 1 - board[i0][j0]) {
                board[i0 - 1][j0 + 1] = -1;
                doMove = true;
            } else if (i == i0 + 2 && j == j0 - 2 && board[i0 + 1][j0 - 1] == 1 - board[i0][j0]) {
                board[i0 + 1][j0 - 1] = -1;
                doMove = true;
            } else if (i == i0 - 2 && j == j0 - 2 && board[i0 - 1][j0 - 1] == 1 - board[i0][j0]) {
                board[i0 - 1][j0 - 1] = -1;
                doMove = true;
            }
            if (doMove) {
                board[i][j] = board[i0][j0];
                board[i0][j0] = -1;
                currentPlayer = 1 - currentPlayer;
                return true;
            }
        }
        return false;
    }
}
