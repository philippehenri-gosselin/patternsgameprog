/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap02.pacman;

import java.util.List;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;


public class CharactersTest {
    
    Random rand = new Random();
    
    public CharactersTest() {
    }

    @Test
    public void testAccess() {
        Characters chars = new Characters();
        chars.add(new Pacman());
        chars.add(new Ghost(0));
        chars.add(new Ghost(1));
        chars.add(new Ghost(2));
        chars.add(new Ghost(3));
        assertEquals(5,chars.size());
        
        List<MobileElement> list = chars.get(0,0);
        assertTrue(list.get(0) instanceof Pacman);
        assertTrue(list.get(1) instanceof Ghost);
        assertTrue(list.get(2) instanceof Ghost);
        assertTrue(list.get(3) instanceof Ghost);
        assertTrue(list.get(4) instanceof Ghost);
        
        for (int i=0;i<5;i++) {
            MobileElement me = list.get(i);
            int x = rand.nextInt(100);
            int y = rand.nextInt(100);
            me.setX(x);
            me.setY(y);
            assertEquals(x,list.get(i).getX());
            assertEquals(y,list.get(i).getY());
        }
    }
    
}
