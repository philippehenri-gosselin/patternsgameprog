/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.server;

import javax.json.JsonObject;

public class ServiceQuery {

    private final String method;

    private final String path;

    private JsonObject input;

    private JsonObject output;

    public ServiceQuery(String method, String url) {
        this.method = method;
        this.path = url;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public JsonObject getInput() {
        return input;
    }

    public JsonObject getOutput() {
        return output;
    }

    public void setInput(JsonObject input) {
        this.input = input;
    }

    public void setOutput(JsonObject output) {
        this.output = output;
    }
}
