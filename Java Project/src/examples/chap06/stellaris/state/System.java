/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.stellaris.state;

import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class System {

    private final Galaxy galaxy;
    
    private final int id;
    
    private String name;

    private int x;

    private int y;

    private ArrayList<Planet> planets = new ArrayList();

    public System(Galaxy galaxy,int id,String name, int x, int y) {
        this.galaxy = galaxy;
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
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
    
    public String getPath() {
        return galaxy.getPath()+"/system/"+(id+1);
    }
    
    public void fromJson(JsonObject json) {
        name = json.getString("name");
        x = json.getInt("x");
        y = json.getInt("y");
    }    
    
    public void toJson(JsonObjectBuilder output) {
        output.add("name",name);
        output.add("x",x);
        output.add("y",y);
        output.add("path",getPath());
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        for (int i=0;i<planets.size();i++) {
            Planet planet = planets.get(i);
            if (planet == null) {
                continue;
            }
            JsonObjectBuilder jsonPlanet = Json.createObjectBuilder();
            jsonPlanet.add("name",planet.getName());
            jsonPlanet.add("type",planet.getClass().getSimpleName());
            jsonPlanet.add("path",planet.getPath());
            objectBuilder.add(String.valueOf(i+1),jsonPlanet);
        }
        output.add("planets",objectBuilder);
    }      
    
}
