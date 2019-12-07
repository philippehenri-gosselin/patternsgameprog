/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap04.stellaris;

import examples.chap02.stellaris.Galaxy;
import examples.chap02.stellaris.Gaseous;
import examples.chap02.stellaris.Habitable;
import examples.chap02.stellaris.System;
import org.junit.Test;
import static org.junit.Assert.*;

public class GalaxyLoaderTest {

    public static String[] data = {
        "System", "Alpha", 
            "Habitable", "Alpha1",
            "Gaseous", "Alpha2",
            "End",
        "System", "Beta",
            "Gaseous", "Beta1",
            "Gaseous", "Beta2",
            "Gaseous", "Beta3",
            "End",
        "System", "Gamma",
            "Habitable", "Gamma1",
            "End",
        "End"        
    };

    @Test
    public void test() {
        Galaxy galaxy = new GalaxyLoader(data).load();
        
        assertEquals("Alpha",galaxy.getSystem(0).getName());
        assertEquals("Beta",galaxy.getSystem(1).getName());
        assertEquals("Gamma",galaxy.getSystem(2).getName());
        
        System alpha = galaxy.getSystem(0);
        assertTrue(alpha.getPlanet(0) instanceof Habitable);
        assertTrue(alpha.getPlanet(1) instanceof Gaseous);

        System beta = galaxy.getSystem(1);
        assertTrue(beta.getPlanet(0) instanceof Gaseous);
        assertTrue(beta.getPlanet(1) instanceof Gaseous);
        assertTrue(beta.getPlanet(2) instanceof Gaseous);

        System gamma = galaxy.getSystem(2);
        assertTrue(gamma.getPlanet(0) instanceof Habitable);
    }
    
}
