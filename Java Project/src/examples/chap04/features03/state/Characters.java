/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.features03.state;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

public class Characters implements List<MobileElement>, Serializable {

    private List<MobileElement> chars;

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

    public List<MobileElement> get(int x, int y) {
        List<MobileElement> list = new ArrayList();
        for (MobileElement me : chars) {
            if (me.getX() == x && me.getY() == y) {
                list.add(me);
            }
        }
        return list;
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

    @Override
    public int size() {
        return chars.size();
    }

    @Override
    public boolean isEmpty() {
        return chars.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return chars.contains(o);
    }

    @Override
    public Iterator<MobileElement> iterator() {
        return chars.iterator();
    }

    @Override
    public boolean add(MobileElement e) {
        return chars.add(e);
    }

    @Override
    public void clear() {
        chars.clear();
    }

    @Override
    public MobileElement get(int index) {
        return chars.get(index);
    }

    @Override
    public MobileElement set(int index, MobileElement element) {
        return chars.set(index, element);
    }

    @Override
    public void add(int index, MobileElement element) {
        chars.add(index, element);
    }

    @Override
    public MobileElement remove(int index) {
        return chars.remove(index);
    }

    @Override
    public Object[] toArray() {
        return chars.toArray();
    }

    @Override
    public <T> T[] toArray(T[] arg0) {
        return chars.toArray(arg0);
    }

    @Override
    public boolean remove(Object o) {
        return chars.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return chars.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends MobileElement> c) {
        return chars.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends MobileElement> c) {
        return chars.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return chars.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return chars.retainAll(c);
    }

    @Override
    public int indexOf(Object o) {
        return chars.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return chars.lastIndexOf(o);
    }

    @Override
    public ListIterator<MobileElement> listIterator() {
        return chars.listIterator();
    }

    @Override
    public ListIterator<MobileElement> listIterator(int index) {
        return chars.listIterator(index);
    }

    @Override
    public List<MobileElement> subList(int fromIndex, int toIndex) {
        return chars.subList(fromIndex, toIndex);
    }

}
