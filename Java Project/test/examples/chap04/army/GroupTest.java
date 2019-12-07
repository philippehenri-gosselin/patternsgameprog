/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.army;

import examples.chap04.army.Group;
import examples.chap04.army.Unit;
import examples.chap04.army.Infantry;
import org.junit.Test;
import static org.junit.Assert.*;

public class GroupTest {

    @Test
    public void testSomeMethod() {
        Unit unit1 = new Infantry();
        Unit unit2 = new Infantry();
        Unit unit3 = new Infantry();
        Unit unit4 = new Infantry();
        
        unit1.move(1, 4);
        unit2.move(2, 3);
        unit3.move(3, 2);
        unit4.move(4, 1);
        
        assertEquals(1,unit1.getX());
        assertEquals(2,unit2.getX());
        assertEquals(3,unit3.getX());
        assertEquals(4,unit4.getX());

        assertEquals(4,unit1.getY());
        assertEquals(3,unit2.getY());
        assertEquals(2,unit3.getY());
        assertEquals(1,unit4.getY());
        
        Group group = new Group();
        group.add(unit1);
        group.add(unit2);
        group.add(unit3);
        group.add(unit4);
        
        Unit groupAsUnit = (Unit)group;
        groupAsUnit.move(1, -1);

        assertEquals(2,unit1.getX());
        assertEquals(3,unit2.getX());
        assertEquals(4,unit3.getX());
        assertEquals(5,unit4.getX());

        assertEquals(3,unit1.getY());
        assertEquals(2,unit2.getY());
        assertEquals(1,unit3.getY());
        assertEquals(0,unit4.getY());        
    }    
}
