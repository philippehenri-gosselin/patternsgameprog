/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap05.ai01.state.mutable;

import examples.chap05.ai01.state.ElementFactory;
import examples.chap05.ai01.state.Direction;
import examples.chap05.ai01.state.Space;
import examples.chap05.ai01.state.SpaceTypeId;
import examples.chap05.ai01.state.StaticElement;
import examples.chap05.ai01.state.Wall;
import examples.chap05.ai01.state.WallTypeId;
import examples.chap05.ai01.state.World;
import examples.chap05.ai01.state.WorldIterator;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.Arrays;

public class MutableWorld implements World, Serializable {
    
    protected int width;

    protected int height;

    protected MutableStaticElement[][] elements;

    protected ElementFactory factory;
    
    public MutableWorld(int width, int height) {
        this.width = width;
        this.height = height;
        elements = new MutableStaticElement[width][height];
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                elements[i][j] = new MutableSpace(SpaceTypeId.EMPTY);
            }
        }
    }

    public ElementFactory getFactory() {
        return factory;
    }
    
    public void setFactory(ElementFactory factory) {
        this.factory = factory;
    }
        
    @Override
    public void init(int[][] level) {
        for (int y=0;y<height;y++) {
            for (int x=0;x<width;x++) {
                MutableElement element = factory.create(level[y][x]);
                elements[x][y] = (MutableStaticElement)element;
            }
        }
    }    

    @Override
    public void set(int x, int y, StaticElement e) {
        if (x < 0 || x >= width || y < 0 || y >= height)
            throw new IllegalArgumentException("Coordonnées " + x + "," + y + " invalides");
        if (!(e instanceof MutableStaticElement)) {
            throw new IllegalArgumentException("Seul les éléments modifiables peuvent être ajoutés");
        }
        elements[x][y] = (MutableStaticElement)e;
    }

    @Override
    public StaticElement get(int x, int y, Direction d) {
        switch(d) {
            case NONE:
                while (x < 0) x += width;
                while (x >= width) x -= width;
                while (y < 0) y += height;
                while (y >= height) y -= height;
                return elements[x][y];
            case NORTH:
                return get(x, y - 1, Direction.NONE);
            case SOUTH:
                return get(x, y + 1, Direction.NONE);
            case EAST:
                return get(x + 1, y, Direction.NONE);
            case WEST:
                return get(x - 1, y, Direction.NONE);
        }
        throw new RuntimeException("Invalid Direction");
    }

    @Override
    public WorldIterator iterator() {
        return new MutableWorldIterator(this);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public World clone() {
        MutableWorld other = new MutableWorld(width,height);
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                other.elements[i][j] = (MutableStaticElement)elements[i][j].clone();
            }
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
        final MutableWorld other = (MutableWorld) obj;
        if (this.width != other.width) {
            return false;
        }
        if (this.height != other.height) {
            return false;
        }
        if (!Arrays.deepEquals(this.elements, other.elements)) {
            return false;
        }
        return true;
    }
    
    private Object writeReplace() {
        return new WorldProxy(this);
    }

    private static final long serialVersionUID = 2459076419736506590L;    
    private static class WorldProxy implements Externalizable
    {        
        private World world;
        
        public WorldProxy() {
        }
        
        public WorldProxy(World world) {
            this.world = world;
        }

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeInt(world.getWidth());
            out.writeInt(world.getHeight());
            for (StaticElement se : world) {
                if (se instanceof Wall) {
                    Wall wall = (Wall)se;
                    out.writeChar('w');
                    out.writeInt(wall.getWallTypeId().getCode());
                }
                else if (se instanceof Space) {
                    Space space = (Space)se;
                    out.writeChar('s');
                    out.writeInt(space.getSpaceTypeId().getCode());
                }
                else {
                    throw new IOException("Unable to serialize an element");
                }
            }
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            int width = in.readInt();
            int height = in.readInt();
            world = new MutableWorld(width,height);
            for (int y=0;y<world.getHeight();y++) {
                for (int x=0;x<world.getWidth();x++) {
                    char type = in.readChar();
                    if (type == 'w') {
                        WallTypeId id = WallTypeId.fromCode(in.readInt());
                        world.set(x, y, new MutableWall(id));
                    }
                    else if (type == 's') {
                        SpaceTypeId id = SpaceTypeId.fromCode(in.readInt());
                        world.set(x, y, new MutableSpace(id));
                    }
                    else {
                        throw new IOException("Unable to deserialize an element");
                    }
                }
            }
        }
        
        private Object readResolve() {
            return world;
        }
        
    }

}
