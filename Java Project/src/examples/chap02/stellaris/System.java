/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap02.stellaris;

import java.util.ArrayList;

public class System {

    private String name;

    private int x;

    private int y;

    private ArrayList<Planet> planets;

    public System(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
        planets = new ArrayList();
    }

    public void addPlanet(Planet planet) {
        if (findPlanet(planet.getName()) != null)
            throw new IllegalArgumentException("La planète " + name + " existe déjà dans le système " + this.name);
        planets.add(planet);
    }

    public Planet getPlanet(int index) {
        if (index >= planets.size())
            throw new IllegalArgumentException("Il n'y a pas de planète à l'indice " + index + " dans le système " + name);
        return planets.get(index);
    }

    public Planet findPlanet(String name) {
        for (Planet planet : planets) {
            if (planet.getName().equals(name)) {
                return planet;
            }
        }
        return null;
    }

    public int getPlanetCount() {
        return planets.size();
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
