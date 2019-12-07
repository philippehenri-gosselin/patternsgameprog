/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.stellaris.state;

import javax.json.JsonObjectBuilder;

public class Connection {

    private int parsecs;

    private int system1;

    private int system2;

    public Connection(int parsecs, int system1, int system2) {
        this.parsecs = parsecs;
        this.system1 = system1;
        this.system2 = system2;
    }

    public int getParsecs() {
        return parsecs;
    }

    public int getSystem1() {
        return system1;
    }

    public int getSystem2() {
        return system2;
    }

    public void setParsecs(int parsecs) {
        this.parsecs = parsecs;
    }

    public void setSystem1(int system1) {
        this.system1 = system1;
    }

    public void setSystem2(int system2) {
        this.system2 = system2;
    }
    
    public void toJson(JsonObjectBuilder output) {
        output.add("parsecs",parsecs);
        output.add("system1",system1);
        output.add("system2",system2);
    }      
    
}
