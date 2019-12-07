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
import java.util.Arrays;

public class World implements Iterable<StaticElement>, Serializable {

    private int width;

    private int height;

    private StaticElement[][] elements;

    private ElementFactory factory;
    
    public World(int width, int height) {
        this.width = width;
        this.height = height;
        elements = new StaticElement[width][height];
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                elements[i][j] = new Space(SpaceTypeId.EMPTY);
            }
        }
    }
    
    public void init(int[][] level) {
        for (int y=0;y<height;y++) {
            for (int x=0;x<width;x++) {
                Element element = factory.create(level[y][x]);
                elements[x][y] = (StaticElement)element;
            }
        }
    }    

    public void set(int x, int y, StaticElement e) {
        if (x < 0 || x >= width || y < 0 || y >= height)
            throw new IllegalArgumentException("Coordonnées " + x + "," + y + " invalides");
        elements[x][y] = e;
    }

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

    public WorldIterator iterator() {
        return new WorldIterator(this);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ElementFactory getFactory() {
        return factory;
    }
    
    public void setFactory(ElementFactory factory) {
        this.factory = factory;
    }
    
    public StaticElement[][] getElements() {
        return elements;
    }
    
    @Override
    public World clone() {
        World other = new World(width,height);
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                other.elements[i][j] = (StaticElement)elements[i][j].clone();
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
        final World other = (World) obj;
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
            for (int y=0;y<world.getHeight();y++) {
                for (int x=0;x<world.getWidth();x++) {
                    StaticElement se = world.get(x,y,Direction.NONE);
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
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            int width = in.readInt();
            int height = in.readInt();
            world = new World(width,height);
            for (int y=0;y<world.getHeight();y++) {
                for (int x=0;x<world.getWidth();x++) {
                    char type = in.readChar();
                    if (type == 'w') {
                        WallTypeId id = WallTypeId.fromCode(in.readInt());
                        world.set(x, y, new Wall(id));
                    }
                    else if (type == 's') {
                        SpaceTypeId id = SpaceTypeId.fromCode(in.readInt());
                        world.set(x, y, new Space(id));
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
