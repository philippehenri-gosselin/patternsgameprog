/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.stellaris.state;

import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

public class Habitable extends Planet {

    private ArrayList<Building> buildings = new ArrayList();

    public Habitable(System system, int id, String name) {
        super(system, id, name);
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(ArrayList<Building> buildings) {
        this.buildings = buildings;
    }
    
    public void toJson(JsonObjectBuilder output) {
        super.toJson(output);
        output.add("type", "Habitable");
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Building building: buildings) {
            JsonObjectBuilder jsonBuilding = Json.createObjectBuilder();
            building.toJson(jsonBuilding);
            arrayBuilder.add(jsonBuilding);
        }
        output.add("buildings",arrayBuilder);
    }      

}
