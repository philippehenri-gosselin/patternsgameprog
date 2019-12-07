/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap05.ai03.state.mutable;

import examples.chap05.ai03.state.Characters;
import examples.chap05.ai03.state.CharactersIterator;
import examples.chap05.ai03.state.Direction;
import examples.chap05.ai03.state.Ghost;
import examples.chap05.ai03.state.GhostStatus;
import examples.chap05.ai03.state.MobileElement;
import examples.chap05.ai03.state.Pacman;
import examples.chap05.ai03.state.PacmanStatus;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MutableCharacters implements Characters, Serializable {
    
    protected List<MutableMobileElement> chars = new ArrayList();;

    @Override
    public void init(int[][] level) {
        chars.clear();
        chars.add(new MutablePacman());
        for (int y=0;y<level.length;y++) {
            for (int x=0;x<level[y].length;x++) {
                int code = level[y][x];
                if (code == 21) {
                    chars.get(0).setX(x);
                    chars.get(0).setY(y);
                }
                else if (code >= 22 && code <= 25) {
                    MutableGhost ghost = new MutableGhost(code-22);
                    ghost.setX(x);
                    ghost.setY(y);
                    chars.add(ghost);
                }
            }
        }
    }
    
    @Override
    public void add(MobileElement me) {
        if (!(me instanceof MutableMobileElement))
            throw new IllegalArgumentException("Seul des éléments modifiables peuvent être ajouté");
        chars.add((MutableMobileElement)me);
    }
    
    @Override
    public void set(int index, MobileElement me) {
        if (!(me instanceof MutableMobileElement))
            throw new IllegalArgumentException("Seul des éléments modifiables peuvent être ajouté");
        chars.set(index, (MutableMobileElement)me);
    }
    
    @Override
    public MobileElement get(int index) {
        return chars.get(index);
    }
    
    @Override
    public Pacman getPacman() {
        if (chars.isEmpty())
            return null;
        MobileElement me = chars.get(0);
        if (!(me instanceof Pacman)) 
            return null;
        return (Pacman)me;
    }
    
    @Override
    public Ghost getGhost(int index) {
        if (index >= chars.size())
            return null;
        MobileElement me = chars.get(index);
        if (!(me instanceof Ghost)) 
            return null;
        return (Ghost)me;
    }

    @Override
    public int size() {
        return chars.size();
    }

    @Override
    public boolean isEmpty() {
        return chars.isEmpty();
    }
    
    @Override
    public CharactersIterator iterator() {
        return new MutableCharactersIterator(this);
    }
    
    @Override
    public Characters clone() {
        MutableCharacters other = new MutableCharacters();
        for (MutableMobileElement me : chars) {
            other.chars.add((MutableMobileElement)me.clone());
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
        final MutableCharacters other = (MutableCharacters) obj;
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
        private MutableCharacters chars;
        
        public CharactersProxy() {
        }
        
        public CharactersProxy(MutableCharacters chars) {
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
            chars = new MutableCharacters();
            for (int i=0;i<count;i++) {
                MobileElement me;
                char type = in.readChar();
                if (type == 'p') {
                    MutablePacman pacman = new MutablePacman();
                    pacman.setStatus(PacmanStatus.fromCode(in.readInt()));
                    me = pacman;
                }
                else if (type == 'g') {
                    MutableGhost ghost = new MutableGhost(in.readInt());
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
