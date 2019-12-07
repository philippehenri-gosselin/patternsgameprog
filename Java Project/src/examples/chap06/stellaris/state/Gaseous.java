/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.stellaris.state;

import javax.json.JsonObjectBuilder;

public class Gaseous extends Planet {

    public Gaseous(System system, int id, String name) {
        super(system, id, name);
    }
    
    public void toJson(JsonObjectBuilder output) {
        super.toJson(output);
        output.add("type", "Gaseous");
    }

}
