/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.stellaris.state;

import javax.json.JsonObjectBuilder;

public class Building {

    private BuildingType type;

    private int level;

    public Building(BuildingType type, int level) {
        this.type = type;
        this.level = level;
    }

    public BuildingType getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public void setType(BuildingType type) {
        this.type = type;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    
    public void toJson(JsonObjectBuilder output) {
        output.add("type", type.toString());
        output.add("level", level);
    }  

}
