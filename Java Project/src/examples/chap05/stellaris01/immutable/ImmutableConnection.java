/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.stellaris01.immutable;

import examples.chap05.stellaris01.Connection;
import examples.chap05.stellaris01.mutable.MutableConnection;

public final class ImmutableConnection implements Connection {
    
    private final MutableConnection connection;

    public ImmutableConnection(MutableConnection connection) {
        this.connection = connection;
    }

    public int getParsecs() {
        return connection.getParsecs();
    }

    public int getSystem1() {
        return connection.getSystem1();
    }
    
    public int getSystem2() {
        return connection.getSystem2();
    }
    
    public void setParsecs(int parsecs) {
        throw new IllegalAccessError();
    }

    public void setSystem1(int system1) {
        throw new IllegalAccessError();
    }

    public void setSystem2(int system2) {
        throw new IllegalAccessError();
    }

    public ImmutableConnection toImmutable() {
        return this;
    }
}
