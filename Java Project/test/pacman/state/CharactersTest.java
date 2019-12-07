/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.state;

import java.util.List;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;
import pacman.state.mutable.MutableCharacters;
import pacman.state.mutable.MutableGhost;
import pacman.state.mutable.MutablePacman;


public class CharactersTest {
    
    Random rand = new Random();
    
    public CharactersTest() {
    }

    @Test
    public void testAccess() {
        Characters chars = new MutableCharacters();
        chars.add(new MutablePacman());
        chars.add(new MutableGhost(0));
        chars.add(new MutableGhost(1));
        chars.add(new MutableGhost(2));
        chars.add(new MutableGhost(3));
        assertEquals(5,chars.size());
        
        assertTrue(chars.get(0) instanceof Pacman);
        assertTrue(chars.get(1) instanceof Ghost);
        assertTrue(chars.get(2) instanceof Ghost);
        assertTrue(chars.get(3) instanceof Ghost);
        assertTrue(chars.get(4) instanceof Ghost);
        
        for (int i=0;i<5;i++) {
            MobileElement me = chars.get(i);
            int x = rand.nextInt(100);
            int y = rand.nextInt(100);
            me.setX(x);
            me.setY(y);
            assertEquals(x,chars.get(i).getX());
            assertEquals(y,chars.get(i).getY());
        }
    }
    
}
