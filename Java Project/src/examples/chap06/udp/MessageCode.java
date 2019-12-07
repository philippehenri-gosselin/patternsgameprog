/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap06.udp;

public enum MessageCode {
    
    NEW_PLAYER((byte)0),
    PLAYER_COORD((byte)1),
    PLAYER_COORDS((byte)2);
    
    byte code;
    MessageCode(byte code) {
        this.code = code;
    }
    
    public byte getCode() {
        return code;
    }
    
}
