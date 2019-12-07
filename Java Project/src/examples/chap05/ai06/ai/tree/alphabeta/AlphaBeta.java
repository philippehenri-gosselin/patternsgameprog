/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.ai06.ai.tree.alphabeta;

import examples.chap05.ai06.ai.tree.Node;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

public class AlphaBeta {

    private Queue<Task> todo;

    public List<Node> run(Node root) {
        todo = Collections.asLifoQueue(new ArrayDeque());
        for (Node child : root) {
            todo.add(new ExploreMinTask(child));
        }
        while (!todo.isEmpty()) {
            Task task = todo.poll();
            task.run(todo);
        }
        int max = Integer.MIN_VALUE;
        for(Node child : root) {
            if (!child.hasValue())
                throw new RuntimeException("No value");
            int value = child.getValue();
            if (value > max) {
                max = value;
            }
        }
        ArrayList<Node> bestNodes = new ArrayList();
        for(Node child : root) {
            if (child.getValue() == max) {
                bestNodes.add(child);
            }
        }
        root.setValue(max);      
        return bestNodes;    
    }
}
