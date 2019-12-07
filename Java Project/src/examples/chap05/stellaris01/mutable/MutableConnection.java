/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.stellaris01.mutable;

import examples.chap05.stellaris01.Connection;
import examples.chap05.stellaris01.immutable.ImmutableConnection;

public class MutableConnection implements Connection {

    private int parsecs;

    private int system1;

    private int system2;

    public MutableConnection(int parsecs, int system1, int system2) {
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

    public ImmutableConnection toImmutable() {
        return new ImmutableConnection(this);
    }
}
