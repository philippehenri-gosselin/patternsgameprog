/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.immutable01.state;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Characters implements Iterable<MobileElement>,Serializable {

    protected List<MobileElement> chars;

    public Characters() {
        chars = new ArrayList();
    }
    
    public void init(int[][] level) {
        chars.clear();
        chars.add(new Pacman());
        for (int y=0;y<level.length;y++) {
            for (int x=0;x<level[y].length;x++) {
                int code = level[y][x];
                if (code == 21) {
                    chars.get(0).setX(x);
                    chars.get(0).setY(y);
                }
                else if (code >= 22 && code <= 25) {
                    Ghost ghost = new Ghost(code-22);
                    ghost.setX(x);
                    ghost.setY(y);
                    chars.add(ghost);
                }
            }
        }
    }
    
    public void add(MobileElement me) {
        chars.add(me);
    }
    
    public void set(int index, MobileElement me) {
        chars.set(index, me);
    }
    
    public MobileElement get(int index) {
        return chars.get(index);
    }
    
    public Pacman getPacman() {
        if (chars.isEmpty())
            return null;
        MobileElement me = chars.get(0);
        if (!(me instanceof Pacman)) 
            return null;
        return (Pacman)me;
    }
    
    public Ghost getGhost(int index) {
        if (index >= chars.size())
            return null;
        MobileElement me = chars.get(index);
        if (!(me instanceof Ghost)) 
            return null;
        return (Ghost)me;
    }

    public List<MobileElement> getChars() {
        return chars;
    }

    public int size() {
        return chars.size();
    }

    public boolean isEmpty() {
        return chars.isEmpty();
    }
    
    @Override
    public Iterator<MobileElement> iterator() {
        return new CharactersIterator(this);
    }
    
    @Override
    public Characters clone() {
        Characters other = new Characters();
        for (MobileElement me : chars) {
            other.chars.add((MobileElement)me.clone());
        }
        return other;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Characters other = (Characters) obj;
        if (!Objects.equals(this.chars, other.chars)) {
            return false;
        }
        return true;
    }
    
    private Object writeReplace() {
        return new CharactersProxy(this);
    }

    private static final long serialVersionUID = 2772374661623881071L;    
    private static class CharactersProxy implements Externalizable
    {        
        private Characters chars;
        
        public CharactersProxy() {
        }
        
        public CharactersProxy(Characters chars) {
            this.chars = chars;
        }

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeInt(chars.size());
            for (MobileElement me : chars) {
                if (me instanceof Pacman) {
                    Pacman pacman = (Pacman)me;
                    out.writeChar('p');
                    out.writeInt(pacman.getStatus().getCode());
                }
                else if (me instanceof Ghost) {
                    Ghost ghost = (Ghost)me;
                    out.writeChar('g');
                    out.writeInt(ghost.getColor());
                    out.writeInt(ghost.getStatus().getCode());
                }
                else {
                    throw new IOException("Unable to serialize an element");
                }
                out.writeInt(me.getDirection().getCode());
                out.writeInt(me.getSpeed());
                out.writeInt(me.getPosition());
                out.writeInt(me.getStatusTime());
                out.writeInt(me.getX());
                out.writeInt(me.getY());
            }
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            int count = in.readInt();
            chars = new Characters();
            for (int i=0;i<count;i++) {
                MobileElement me;
                char type = in.readChar();
                if (type == 'p') {
                    Pacman pacman = new Pacman();
                    pacman.setStatus(PacmanStatus.fromCode(in.readInt()));
                    me = pacman;
                }
                else if (type == 'g') {
                    Ghost ghost = new Ghost(in.readInt());
                    ghost.setStatus(GhostStatus.fromCode(in.readInt()));
                    me = ghost;
                }
                else {
                    throw new IOException("Unable to deserialize an element");
                }
                me.setDirection(Direction.fromCode(in.readInt()));
                me.setSpeed(in.readInt());
                me.setPosition(in.readInt());
                me.setStatusTime(in.readInt());
                me.setX(in.readInt());
                me.setY(in.readInt());
                chars.add(me);
            }
        }
        
        private Object readResolve() {
            return chars;
        }
    }


}
