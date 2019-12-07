/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.network02.server;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class Player {

    private String name;

    private boolean ai;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAi() {
        return ai;
    }

    public void setAi(boolean ai) {
        this.ai = ai;
    }
    
    public void toJson(JsonObjectBuilder builder) {
        builder
            .add("name",name)
            .add("ai", ai);
    }

    void setValues(JsonObject input) {
        if (input.containsKey("name")) {
            name = input.getString("name");
        }
        if (input.containsKey("ai")) {
            ai = input.getBoolean("ai");
        }
    }
}
