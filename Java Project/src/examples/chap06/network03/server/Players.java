/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.network03.server;

import java.util.ArrayList;
import java.util.List;

public class Players {
 
    private List<Player> players = new ArrayList();

    public Players() {
        for (int i=0;i<5;i++) {
            players.add(null);
        }    
    }

    public synchronized Player get(int index) {
        if (index >= players.size() || players.get(index) == null) {
            return null;
        }
        return players.get(index).clone();
    }

    public synchronized List<Player> getAll() {
        List<Player> clone = new ArrayList();
        for (int i=0;i<players.size();i++) {
            Player player = players.get(i);
            if (player != null)
                clone.add(player.clone());
            else
                clone.add(null);
        }
        return clone;
    }
    
    public synchronized int find(String playerName) {
        for (int i=0;i<players.size();i++) {
            Player player = players.get(i);
            if (player != null && player.getName().equals(playerName)) {
                return i;
            }
        }
        return -1;
    }    
    
    public synchronized boolean set(int index,Player player) {
        if (index < 0 || index >= players.size())
            return false;
        int playerPrevIndex = find(player.getName());
        if (playerPrevIndex >= 0) {
            Player prevPlayer = players.get(index);
            players.set(playerPrevIndex, prevPlayer);
        }
        players.set(index, player);
        return true;
    }
    
    public synchronized int add(Player player) {
        int found = -1;
        for (int i=0;i<players.size();i++) {
            if (players.get(i) == null) {
                found = i;
                break;
            }
        }
        if (found < 0)
            return -1;
        players.set(found,player);
        return found;
    }
    
    public synchronized boolean remove(int index) {
        if (index < 0 || index >= players.size())
            return false;
        players.set(index, null);
        return true;
    }

    public synchronized void removeAll() {
        for(int i=0;i<players.size();i++) {
            players.set(i,null);
        }    
    }

}
