/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.army;

import java.util.ArrayList;
import java.util.List;

public class Group extends Unit {

    private List<Unit> units = new ArrayList();

    public void move(int x, int y) {
        for (Unit unit : units) {
            unit.move(x, y);
        }
    }

    public void add(Unit unit) {
        units.add(unit);
    }

    public int size() {
        return units.size();
    }

    public Unit get(int i) {
        return units.get(i);
    }
}
