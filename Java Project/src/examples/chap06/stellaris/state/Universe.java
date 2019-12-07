/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.stellaris.state;

import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class Universe {

    private List<Galaxy> galaxies = new ArrayList();
    
    public int getGalaxyCount() {
        return galaxies.size();
    }
    
    public Galaxy getGalaxy(int index) {
        return galaxies.get(index);
    }
    
    public void toJson(JsonObjectBuilder output) {
        for (int i=0;i<galaxies.size();i++) {
            Galaxy galaxy = galaxies.get(i);
            if (galaxy == null)
                continue;
            JsonObjectBuilder jsonGalaxy = Json.createObjectBuilder();
            jsonGalaxy.add("name",galaxy.getName());
            jsonGalaxy.add("systems",galaxy.getSystemCount());
            jsonGalaxy.add("path",galaxy.getPath());
            output.add(String.valueOf(i+1),jsonGalaxy);
        }
    }        
    
    public int createGalaxy(String name) {
        int found = -1;
        for (int i=0;i<galaxies.size();i++) {
            if (galaxies.get(i) == null) {
                found = i;
                break;
            }
        }
        if (found >= 0) {
            galaxies.set(found, new Galaxy(this,found,name));
            return found;
        }
        else {
            int id = galaxies.size();
            galaxies.add(new Galaxy(this,id,name));
            return id;
        }
    }
    
    public boolean removeGalaxy(int index) {
        if (index < 0 || index >= galaxies.size())
            return false;
        Galaxy galaxy = galaxies.get(index);
        if (galaxy == null)
            return false;
        galaxies.set(index, null);
        return true;
    }
}
