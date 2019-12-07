/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap02.civilization;

import org.junit.Test;
import static org.junit.Assert.*;

public class CitiesTest {
    
    public CitiesTest() {
    }

    @Test
    public void testCities() {        
        Cities cities = new Cities();
        cities.add(new Location(1, 2), new City("Paris"));
        cities.add(new Location(5, 3), new City("New York"));
        assertEquals(2,cities.getCities().size());

        City paris = cities.get(new Location(1, 2));
        assertEquals("Paris",paris.getName());
        City newyork = cities.get(new Location(5, 3));
        assertEquals("New York",newyork.getName());

        int i = 0;
        for (City city : cities) {
            if (i == 0) assertEquals("Paris",city.getName());
            else if (i == 1) assertEquals("New York",city.getName());
            i ++;
        }

        paris.getBuildings().add("Librairie");
        paris.getBuildings().add("Baraquements");
        paris.getBuildings().add("Cathédrale");
        i = 0;
        for(String building : paris.getBuildings()) {
            if (i == 0) assertEquals("Baraquements",building);
            else if (i == 1) assertEquals("Cathédrale",building);
            else if (i == 2) assertEquals("Librairie",building);
            i++;
        }
    }
    
}
