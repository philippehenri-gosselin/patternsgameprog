/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.network01.ai.mapproviders;

import examples.chap06.network01.ai.map.Dijkstra;
import examples.chap06.network01.state.Characters;
import examples.chap06.network01.state.MobileElement;
import examples.chap06.network01.state.State;
import examples.chap06.network01.state.World;

public class CharMapMaintainer extends DistanceMapMaintainer {

    private int trackIndex;

    private int trackX;

    private int trackY;
    
    public CharMapMaintainer(int trackIndex) {
        this.trackIndex = trackIndex;
    }

    public void characterChanged(State state, int charIndex) {
        if (charIndex != trackIndex)
            return;
        Characters chars = state.getChars();
        MobileElement me = chars.get(trackIndex);
        if (trackX == me.getX() && trackY == me.getY()) {
            return;
        }
        trackX = me.getX();
        trackY = me.getY();
        World world = state.getWorld();
        Dijkstra dijkstra = new Dijkstra(world,map);
        dijkstra.addSink(trackX, trackY);
        dijkstra.run();
    }
}
