/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.stellaris.state;

import javax.json.JsonObjectBuilder;

public abstract class Planet {

    private final System system;
    
    private final int id;
    
    private String name;

    public Planet(System system,int id,String name) {
        this.system = system;
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return system.getPath()+"/planet/"+(id+1);
    }
    
    public void toJson(JsonObjectBuilder output) {
        output.add("name", name);
        output.add("path", getPath());
    }    

}
