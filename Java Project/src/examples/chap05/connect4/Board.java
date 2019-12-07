/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap05.connect4;

import java.util.Arrays;
import java.util.Objects;

public class Board {

    private final int[][] cells = new int[7][6];
    
    private int currentPlayer;
    
    private int winner;
    
    private CountCache cache1, cache2;
    
    public Board() {
        currentPlayer = 1;
        cache1 = new CountCache(1);
        cache2 = new CountCache(-1);
    }
    
    public int getCurrentPlayer() {
        return currentPlayer;
    }
    
    public int getCell(int j,int i) {
        if (j >= 0 && j < 7 && i >= 0 && i < 6) {
            return cells[j][i];
        }
        return 0;
    }
    
    private void setCell(int j,int i,int player) {
        if (j >= 0 && j < 7 && i >= 0 && i < 6) {
            if (player > 0) {
                if (cells[j][i] != 0) {
                    return;
                }
                cache1.add(j, i);
            }
            else if (player < 0) {
                if (cells[j][i] != 0) {
                    return;
                }
                cache2.add(j, i);
            }
            else {
                if (cells[j][i] == 0) {
                    return;
                }
                if (cells[j][i] > 0) {
                    cache1.remove(j, i);
                }
                else {
                    cache2.remove(j, i);
                }
                winner = 0;
            }
            cells[j][i] = player;
        }
    }
    
    public void reset(int currentPlayer) {
        this.currentPlayer = currentPlayer;
        this.winner = 0;
        this.cache1 = new CountCache(1);
        this.cache2 = new CountCache(-1);
        for (int i=0;i<6;i++) {
            for (int j=0;j<7;j++) {
                cells[j][i] = 0;
            }
        }
    }
    
    public int getPieceCount() {
        int count = 0;
        for (int i=0;i<6;i++) {
            for (int j=0;j<7;j++) {
                if (cells[j][i] != 0) {
                    count ++;
                }
            }
        }
        return count;
    }
    
    public boolean isOver() {
        return winner != 0 || getPieceCount() == 42;
    }    
    
    public boolean canPlay(int column) {
        return cells[column][0] == 0;
    }
    
    public boolean play(int column) {
        if (winner != 0 || column < 0 || column >= 7) {
            return false;
        }
        for (int i=5;i>=0;i--) {
            if (cells[column][i] == 0) {
                setCell(column,i,currentPlayer);
                currentPlayer = -currentPlayer;
                return true;
            }
        }
        return false;
    }
    
    public boolean cancel(int column) {
        if (column < 0 || column >= 7) {
            return false;
        }
        for (int i=0;i<6;i++) {
            if (cells[column][i] != 0) {
                setCell(column,i,0);
                currentPlayer = -currentPlayer;
                return true;
            }
        }
        return false;
    }
    
    public boolean isWinner(int player) {
        return winner == player;
    }
        
    public boolean isWinner() {
        return isWinner(currentPlayer);
    }
    
    public int getWinner() {
        if (isWinner(1))
            return 1;
        if (isWinner(-1))
            return -1;
        return 0;
    }
    
    public void show() {
        System.out.println("Current player: "+currentPlayer);
        for (int i=0;i<6;i++) {
            for (int j=0;j<7;j++) {
                if (cells[j][i] > 0)
                    System.out.print("X ");
                else if (cells[j][i] < 0)
                    System.out.print("O ");
                else
                    System.out.print("  ");
            }
            System.out.println();
        }
        cache1.showLdiag();
        cache2.showLdiag();
    }
    
    public Board clone() {
        Board board = new Board();
        board.currentPlayer = currentPlayer;
        board.winner = winner;
        for (int i=0;i<6;i++) {
            for (int j=0;j<7;j++) {
                board.cells[j][i] = cells[j][i];
            }
        }
        board.cache1 = cache1.clone();
        board.cache2 = cache2.clone();        
        return board;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
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
        final Board other = (Board) obj;
        if (this.currentPlayer != other.currentPlayer) {
            return false;
        }
        if (this.winner != other.winner) {
            return false;
        }
        if (!Arrays.deepEquals(this.cells, other.cells)) {
            return false;
        }
        if (!Objects.equals(this.cache1, other.cache1)) {
            return false;
        }
        if (!Objects.equals(this.cache2, other.cache2)) {
            return false;
        }
        return true;
    }
    
    private final class CountCache 
    {
        public final int player;
        public final int[][] vert = new int[7][3];
        public final int[][] hori = new int[4][6];
        public final int[][] ldia = new int[4][3];
        public final int[][] rdia = new int[4][3];
        
        public CountCache(int player) {
            this.player = player;
        }

        public void add(int j,int i) {
            // Alignement selon les colonnes
            if (i >= 0 && i <= 3) {
                int count = vert[j][0]+1;
                vert[j][0] = count;
                if (count == 4) {
                    winner = player;
                }
            }
            if (i >= 1 && i <= 4) {
                int count = vert[j][1]+1;
                vert[j][1] = count;
                if (count == 4) {
                    winner = player;
                }
            }
            if (i >= 2 && i <= 5) {
                int count = vert[j][2]+1;
                vert[j][2] = count;
                if (count == 4) {
                    winner = player;
                }
            }
            // Alignement selon les lignes
            if (j >= 0 && j <= 3) {
                int count = hori[0][i]+1;
                hori[0][i] = count;
                if (count == 4) {
                    winner = player;
                }
            }
            if (j >= 1 && j <= 4) {
                int count = hori[1][i]+1;
                hori[1][i] = count;
                if (count == 4) {
                    winner = player;
                }
            }
            if (j >= 2 && j <= 5) {
                int count = hori[2][i]+1;
                hori[2][i] = count;
                if (count == 4) {
                    winner = player;
                }
            }
            if (j >= 3 && j <= 6) {
                int count = hori[3][i]+1;
                hori[3][i] = count;
                if (count == 4) {
                    winner = player;
                }
            }
            // Diagonales droites
            if (i >= 0 && i <= 2 && j >= 0 && j <= 3) {
                int count = rdia[j][i] + 1;
                rdia[j][i] = count;
                if (count == 4) {
                    winner = player;
                }
            }
            if (i >= 1 && i <= 3 && j >= 1 && j <= 4) {
                int count = rdia[j-1][i-1] + 1;
                rdia[j-1][i-1] = count;
                if (count == 4) {
                    winner = player;
                }
            }
            if (i >= 2 && i <= 4 && j >= 2 && j <= 5) {
                int count = rdia[j-2][i-2] + 1;
                rdia[j-2][i-2] = count;
                if (count == 4) {
                    winner = player;
                }
            }
            if (i >= 3 && i <= 5 && j >= 3 && j <= 6) {
                int count = rdia[j-3][i-3] + 1;
                rdia[j-3][i-3] = count;
                if (count == 4) {
                    winner = player;
                }
            }
            // Diagonales gauches
            if (i >= 0 && i <= 2 && j >= 3 && j <= 6) {
                int count = ldia[j-3][i] + 1;
                ldia[j-3][i] = count;
                if (count == 4) {
                    winner = player;
                }
            }
            if (i >= 1 && i <= 3 && j >= 2 && j <= 5) {
                int count = ldia[j-2][i-1] + 1;
                ldia[j-2][i-1] = count;
                if (count == 4) {
                    winner = player;
                }
            }
            if (i >= 2 && i <= 4 && j >= 1 && j <= 4) {
                int count = ldia[j-1][i-2] + 1;
                ldia[j-1][i-2] = count;
                if (count == 4) {
                    winner = player;
                }
            }
            if (i >= 3 && i <= 5 && j >= 0 && j <= 3) {
                int count = ldia[j][i-3] + 1;
                ldia[j][i-3] = count;
                if (count == 4) {
                    winner = player;
                }
            }
        }
        
        public void remove(int j,int i) {
            // Alignement selon les colonnes
            if (i >= 0 && i <= 3) {
                vert[j][0] --;
            }
            if (i >= 1 && i <= 4) {
                vert[j][1] --;
            }
            if (i >= 2 && i <= 5) {
                vert[j][2] --;
            }
            // Alignement selon les lignes
            if (j >= 0 && j <= 3) {
                hori[0][i] --;
            }
            if (j >= 1 && j <= 4) {
                hori[1][i] --;
            }
            if (j >= 2 && j <= 5) {
                hori[2][i] --;
            }
            if (j >= 3 && j <= 6) {
                hori[3][i] --;
            }
            // Diagonales droites
            if (i >= 0 && i <= 2 && j >= 0 && j <= 3) {
                rdia[j][i] --;
            }
            if (i >= 1 && i <= 3 && j >= 1 && j <= 4) {
                rdia[j-1][i-1] --;
            }
            if (i >= 2 && i <= 4 && j >= 2 && j <= 5) {
                rdia[j-2][i-2] --;
            }
            if (i >= 3 && i <= 5 && j >= 3 && j <= 6) {
                rdia[j-3][i-3] --;
            }
            // Diagonales gauches
            if (i >= 0 && i <= 2 && j >= 3 && j <= 6) {
                ldia[j-3][i] --;
            }
            if (i >= 1 && i <= 3 && j >= 2 && j <= 5) {
                ldia[j-2][i-1] --;
            }
            if (i >= 2 && i <= 4 && j >= 1 && j <= 4) {
                ldia[j-1][i-2] --;
            }
            if (i >= 3 && i <= 5 && j >= 0 && j <= 3) {
                ldia[j][i-3] --;
            }
        }
        
        public CountCache clone() {
            CountCache cache = new CountCache(player);
            for (int j=0;j<7;j++) {
                for (int i=0;i<3;i++) {
                    cache.vert[j][i] = vert[j][i];
                }
            }
            for (int j=0;j<4;j++) {
                for (int i=0;i<6;i++) {
                    cache.hori[j][i] = hori[j][i];
                }
            }
            for (int j=0;j<4;j++) {
                for (int i=0;i<3;i++) {
                    cache.ldia[j][i] = ldia[j][i];
                }
            }
            for (int j=0;j<4;j++) {
                for (int i=0;i<3;i++) {
                    cache.rdia[j][i] = rdia[j][i];
                }
            }
            return cache;
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
            final CountCache other = (CountCache) obj;
            if (this.player != other.player) {
                return false;
            }
            if (!Arrays.deepEquals(this.vert, other.vert)) {
                return false;
            }
            if (!Arrays.deepEquals(this.hori, other.hori)) {
                return false;
            }
            if (!Arrays.deepEquals(this.ldia, other.ldia)) {
                return false;
            }
            if (!Arrays.deepEquals(this.rdia, other.rdia)) {
                return false;
            }
            return true;
        }
        
        
        
        public void showTab(int[][] tab) {
            int m = tab.length;
            int n = tab[0].length;
            for (int i=0;i<n;i++) {
                for (int j=0;j<m;j++) {
                    System.out.print(""+tab[j][i]);
                }
                System.out.println();
            }        
        }
        
        public void showVert() {
            System.out.println("Cache vertical");
            showTab(vert);
        }
        public void showHori() {
            System.out.println("Cache horizontal");
            showTab(hori);
        }
        public void showRdiag() {
            System.out.println("Cache diagonal droit");
            showTab(rdia);
        }
        public void showLdiag() {
            System.out.println("Cache vertical gauche");
            showTab(ldia);
        }
    }
    
    
    public boolean computeIsWinner(int player) {
        // Alignement selon les colonnes
        for (int j=0;j<7;j++) {
            for (int i=0;i<3;i++) {
                int count = 0;
                for (int k=i;k<i+4;k++) {
                    if (cells[j][k] == player) {
                        count ++;
                    }
                }
                if (count >= 4)
                    return true;
            }
        }
        // Alignement selon les lignes
        for (int i=0;i<6;i++) {
            for (int j=0;j<4;j++) {
                int count = 0;
                for (int k=j;k<j+4;k++) {
                    if (cells[k][i] == player) {
                        count ++;
                    }
                }
                if (count >= 4)
                    return true;
            }
        }
        // Diagonales droites
        for (int i=0;i<3;i++) {
            for (int j=0;j<4;j++) {
                int count = 0;
                for (int k=0;k<4;k++) {
                    if (cells[j+k][i+k] == player) {
                        count ++;
                    }
                }
                if (count >= 4)
                    return true;
            }
        }
        // Diagonales gauches
        for (int i=0;i<3;i++) {
            for (int j=3;j<7;j++) {
                int count = 0;
                for (int k=0;k<4;k++) {
                    if (cells[j-k][i+k] == player) {
                        count ++;
                    }
                }
                if (count >= 4)
                    return true;
            }
        }
        return false;
    }
    
    
}
