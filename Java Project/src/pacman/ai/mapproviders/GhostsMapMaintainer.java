/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.ai.mapproviders;

import pacman.ai.map.Dijkstra;
import pacman.state.Characters;
import pacman.state.Ghost;
import pacman.state.GhostStatus;
import pacman.state.MobileElement;
import pacman.state.State;
import pacman.state.World;

public class GhostsMapMaintainer extends DistanceMapMaintainer {

    private int[] coords;
    
    private GhostStatus status;
    
    public GhostsMapMaintainer(GhostStatus status) {
        this.status = status;
    }

    public void characterChanged(State state, int charIndex) {
        if (charIndex == 0)
            return;
        Characters chars = state.getChars();
        if (coords == null || coords.length != 2*chars.size()) {
            coords = new int[2*chars.size()];
        }
        boolean same = true;
        for (int index=1;index<chars.size();index++) {
            MobileElement me = chars.get(index);
            if (coords[2*index+0] != me.getX() || coords[2*index+1] != me.getY()) {
                same = false;
            }
            coords[2*index+0] = me.getX();
            coords[2*index+1] = me.getY();
        }
        if (same) {
            return;
        }
        World world = state.getWorld();
        Dijkstra dijkstra = new Dijkstra(world,map);
        for (int index=1;index<chars.size();index++) {
            Ghost ghost = chars.getGhost(index);
            if (ghost.getStatus() == status) {
                dijkstra.addSink(ghost.getX(), ghost.getY());
            }
        }
        dijkstra.run();    
    }
}
