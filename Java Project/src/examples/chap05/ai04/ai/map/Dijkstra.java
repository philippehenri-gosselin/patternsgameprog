/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.ai04.ai.map;

import examples.chap05.ai04.state.Direction;
import examples.chap05.ai04.state.Space;
import examples.chap05.ai04.state.World;
import java.util.PriorityQueue;

public class Dijkstra {

    private World world;
    
    private DistanceMap map;

    private PriorityQueue<Point> queue;

    public Dijkstra(World world,DistanceMap map) {
        this.world = world;
        this.map = map;
        queue = new PriorityQueue();
        map.init(Integer.MAX_VALUE);
    }

    public void addSink(int x, int y) {
        queue.add(new Point(x,y,0));
        map.setWeight(x,y,0);
    }

    public void run() {
        while(!queue.isEmpty()) {
            Point point = queue.poll();
            if (map.getWeight(point.x, point.y, Direction.NONE) < point.weight) {
                continue;
            }
            for (Direction direction : Direction.allButNone) {
                if (world.get(point.x, point.y, direction) instanceof Space) {
                    if (map.getWeight(point.x, point.y, direction) > point.weight+1) {
                        Point point2 = point.transform(direction,point.weight+1);
                        queue.add(point2);
                        map.setWeight(point2.x, point2.y, point2.weight);
                    }
                }
            }
        }
    }

}
