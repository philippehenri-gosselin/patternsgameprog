/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap02.stellaris;

import org.junit.Test;
import static org.junit.Assert.*;


public class GalaxyTest {
    
    public GalaxyTest() {
    }

    @Test
    public void test() {
        Galaxy galaxy = new Galaxy();
        
        galaxy.createSystem("Alpha", 10, 20);
        galaxy.createSystem("Beta", 12, 4);
        galaxy.createSystem("Gamma", 23, 17);
        
        int alphai = galaxy.findSystem("Alpha");
        assertTrue(alphai >= 0);
        System alpha = galaxy.getSystem(alphai);
        assertEquals("Alpha",alpha.getName());
        assertEquals(10,alpha.getX());
        assertEquals(20,alpha.getY());

        int betai = galaxy.findSystem("Beta");
        assertTrue(betai >= 0);
        System beta = galaxy.getSystem(betai);
        assertEquals("Beta",beta.getName());
        assertEquals(12,beta.getX());
        assertEquals(4,beta.getY());
        
        int gammai = galaxy.findSystem("Gamma");
        assertTrue(gammai >= 0);
        System gamma = galaxy.getSystem(gammai);        
        assertEquals("Gamma",gamma.getName());
        assertEquals(23,gamma.getX());
        assertEquals(17,gamma.getY());
        
        galaxy.connectSystems("Alpha", "Beta", 1);
        galaxy.connectSystems("Alpha", "Gamma", 2);
        galaxy.connectSystems("Beta", "Gamma", 3);
        
        assertTrue(galaxy.findConnection(alphai, betai) >= 0);
        assertTrue(galaxy.findConnection(alphai, gammai) >= 0);
        assertTrue(galaxy.findConnection(betai, gammai) >= 0);

        assertTrue(galaxy.findConnection(betai, alphai) >= 0);
        assertTrue(galaxy.findConnection(gammai, alphai) >= 0);
        assertTrue(galaxy.findConnection(gammai, betai) >= 0);
        
        try {
            galaxy.connectSystems("False", "Gamma", 45);
            fail("An exception should have been thrown");
        }
        catch(IllegalArgumentException ex) {            
        }
        try {
            galaxy.connectSystems("Alpha", "Beta", 45);
            fail("An exception should have been thrown");
        }
        catch(IllegalArgumentException ex) {            
        }
                
        
    }
    
}
