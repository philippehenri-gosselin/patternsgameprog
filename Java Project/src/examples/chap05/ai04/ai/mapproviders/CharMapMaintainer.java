/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.ai04.ai.mapproviders;

import examples.chap05.ai04.ai.map.Dijkstra;
import examples.chap05.ai04.state.Characters;
import examples.chap05.ai04.state.MobileElement;
import examples.chap05.ai04.state.State;
import examples.chap05.ai04.state.World;

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
