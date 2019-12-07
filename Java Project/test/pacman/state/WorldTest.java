/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package pacman.state;

import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;
import pacman.state.mutable.MutableSpace;
import pacman.state.mutable.MutableWall;
import pacman.state.mutable.MutableWorld;

public class WorldTest {

    Random rand = new Random();
    
    public WorldTest() {
    }

    @Test
    public void testAccess() {
        int width = 3+rand.nextInt(10);
        int height = 3+rand.nextInt(10);
        World world = new MutableWorld(width,height);
        assertEquals(width, world.getWidth());
        assertEquals(height, world.getHeight());
        
        for (int repeat=0;repeat<10;repeat++) {
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);
            world.set(x,y,new MutableSpace(SpaceTypeId.GUM));
            StaticElement se = world.get(x, y, Direction.NONE);
            assertTrue(se instanceof Space);
            Space space = (Space)se;
            assertEquals(SpaceTypeId.GUM,space.getSpaceTypeId());
        }
        
        int x = 1;
        int y = 1;
        world.set(x,y-1,new MutableWall(WallTypeId.BOTTOMRIGHT));
        StaticElement se = world.get(x, y, Direction.NORTH);
        assertTrue(se instanceof Wall);
        Wall wall = (Wall)se;
        assertEquals(WallTypeId.BOTTOMRIGHT,wall.getWallTypeId());
        
        world.set(x,y+1,new MutableWall(WallTypeId.BOTTOMLEFT));
        se = world.get(x, y, Direction.SOUTH);
        assertTrue(se instanceof Wall);
        wall = (Wall)se;
        assertEquals(WallTypeId.BOTTOMLEFT,wall.getWallTypeId());
        
        world.set(x+1,y,new MutableWall(WallTypeId.TOPRIGHT));
        se = world.get(x, y, Direction.EAST);
        assertTrue(se instanceof Wall);
        wall = (Wall)se;
        assertEquals(WallTypeId.TOPRIGHT,wall.getWallTypeId());
        
        world.set(x-1,y,new MutableWall(WallTypeId.TOPLEFT));
        se = world.get(x, y, Direction.WEST);
        assertTrue(se instanceof Wall);
        wall = (Wall)se;
        assertEquals(WallTypeId.TOPLEFT,wall.getWallTypeId());
        
        
    }
    
    @Test
    public void testIterator() {
        int width = 3+rand.nextInt(10);
        int height = 3+rand.nextInt(10);
        
        StaticElement[][] tab = new StaticElement[width][height];
        for (int y=0;y<height;y++) {
            for (int x=0;x<width;x++) {
                if (rand.nextBoolean()) {
                    tab[x][y] = new MutableSpace(SpaceTypeId.GUM);
                }
                else {
                    tab[x][y] = new MutableWall(WallTypeId.TOPLEFT);
                }
            }
        }
        
        World world = new MutableWorld(width,height);
        assertEquals(width, world.getWidth());
        assertEquals(height, world.getHeight());
        for (int y=0;y<height;y++) {
            for (int x=0;x<width;x++) {
                world.set(x,y,tab[x][y]);
            }
        }
        
        int i = 0;
        WorldIterator iterator = world.iterator();
        while(iterator.hasNext()) {
            StaticElement se = iterator.next();
            int x = i % width;
            int y = i / width;
            assertEquals(x,iterator.getX());
            assertEquals(y,iterator.getY());
            assertEquals(se,tab[x][y]);
            i ++;
        }
        
    }
    
}
