/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap02.civilization;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.TreeSet;

public class City {

    private String name;

    private int population;

    private Location location;

    private TreeSet<String> buildings;

    private Deque<String> tobuild;

    public String getName() {
        return name;
    }

    public int getPopulation() {
        return population;
    }

    public Location getLocation() {
        return location;
    }

    public TreeSet<String> getBuildings() {
        return buildings;
    }

    public Deque<String> getTobuild() {
        return tobuild;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setBuildings(TreeSet<String> buildings) {
        this.buildings = buildings;
    }

    public void setTobuild(Deque<String> tobuild) {
        this.tobuild = tobuild;
    }

    public City(String name) {
        this.name = name;
        this.buildings = new TreeSet();
        this.tobuild = new ArrayDeque();
    }
}
