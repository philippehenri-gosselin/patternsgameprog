/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.stellaris02;

import examples.chap02.stellaris.Connection;
import examples.chap02.stellaris.Galaxy;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class ShortestPath {
    
    private final Galaxy galaxy;
    
    private final HashMap<Integer,Integer> weights = new HashMap();
    
    public static class ValuedSystem implements Comparable<ValuedSystem> {
        public int index;
        public int value;
        public ValuedSystem(int index, int value) {
            this.index = index;
            this.value = value;
        }
        public int compareTo(ValuedSystem other) {
            return this.value - other.value;
        }        
    }
    
    private final PriorityQueue<ValuedSystem> queue = new PriorityQueue();
    
    public ShortestPath(Galaxy galaxy) {
        this.galaxy = galaxy;
        for (int i=0;i<galaxy.getSystemCount();i++) {
            weights.put(i, Integer.MAX_VALUE);
        }
    }
    
    HashMap<Integer,Integer> getWeights() {
        return weights;
    }

    public void addSink(String systemName) {
        int index = galaxy.findSystem(systemName);
        queue.add(new ValuedSystem(index,0));
        weights.put(index,0);
    }

    public void run() {
        while(!queue.isEmpty()) {
            ValuedSystem valuedSystem = queue.poll();
            if (valuedSystem.value > weights.get(valuedSystem.index))
                continue;
            for (Connection connection : galaxy.findLeftConnections(valuedSystem.index)) {
                int newWeight = valuedSystem.value + connection.getParsecs();
                int curWeight = weights.get(connection.getSystem2());
                if (curWeight > newWeight) {
                    queue.add(new ValuedSystem(connection.getSystem2(),newWeight));
                    weights.put(connection.getSystem2(),newWeight);
                }
            }
        }        
    }    
    
    public void showWeights() {
        for (Map.Entry<Integer, Integer> weight: weights.entrySet()) {
            System.out.println(
                galaxy.getSystem(weight.getKey()).getName()+
                " : "+
                weight.getValue()
            );
        }
    }
    
    public static void main(String[] args) {
        
        Galaxy galaxy = new Galaxy();

        galaxy.createSystem("A", 10, 0);
        galaxy.createSystem("B", 7, 2);
        galaxy.createSystem("C", 8, 4);
        galaxy.createSystem("D", 0, 3);
        galaxy.createSystem("E", 6, 6);

        galaxy.connectSystems("A", "B", 2);
        galaxy.connectSystems("A", "C", 1);
        galaxy.connectSystems("B", "D", 3);
        galaxy.connectSystems("B", "C", 4);
        galaxy.connectSystems("B", "E", 5);
        galaxy.connectSystems("C", "E", 6);
        galaxy.connectSystems("D", "E", 1);
        
        ShortestPath sp = new ShortestPath(galaxy);
        sp.addSink("A");
        sp.run();
        sp.showWeights();
        
    }    
}
