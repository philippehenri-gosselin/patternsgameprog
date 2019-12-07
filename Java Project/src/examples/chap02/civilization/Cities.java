/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap02.civilization;

import java.util.LinkedHashMap;
import java.util.Iterator;

public class Cities implements Iterable<City> {

    private LinkedHashMap<Location, City> cities;

    public LinkedHashMap<Location, City> getCities() {
        return cities;
    }

    public void setCities(LinkedHashMap<Location, City> cities) {
        this.cities = cities;
    }

    public Cities() {
        cities = new LinkedHashMap();
    }

    public void add(Location location, City city) {
        cities.put(location, city);
    }

    public City get(Location location) {
        return cities.get(location);
    }

    public Iterator<City> iterator() {
        return cities.values().iterator();
    }

}
