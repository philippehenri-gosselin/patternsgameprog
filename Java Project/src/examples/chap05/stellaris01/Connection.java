/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.stellaris01;

import examples.chap05.stellaris01.immutable.ImmutableConnection;

public interface Connection {

    public int getParsecs();

    public int getSystem1();

    public int getSystem2();

    public void setParsecs(int parsecs);

    public void setSystem1(int system1);

    public void setSystem2(int system2);
    
    public ImmutableConnection toImmutable();
}
