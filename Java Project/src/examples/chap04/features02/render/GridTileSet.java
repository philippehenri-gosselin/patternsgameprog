/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.features02.render;

import examples.chap04.features02.state.Element;
import examples.chap04.features02.state.Space;
import examples.chap04.features02.state.Wall;

public class GridTileSet implements TileSet {

    private final Tile[] wallTiles;
    private final Tile[] spaceTiles;
    
    public GridTileSet() {
        wallTiles = new Tile[6];
        wallTiles[0] = new StaticTile(4,1);
        wallTiles[1] = new StaticTile(5,1);
        wallTiles[2] = new StaticTile(2,1);
        wallTiles[3] = new StaticTile(3,1);
        wallTiles[4] = new StaticTile(0,1);
        wallTiles[5] = new StaticTile(1,1);
        
        spaceTiles = new Tile[6];        
        spaceTiles[0] = new StaticTile(0,0);
        spaceTiles[1] = new StaticTile(1,0);
        spaceTiles[2] = new StaticTile(2,0);
        AnimatedTile animatedTile = new AnimatedTile(10);
        animatedTile.addTile(new StaticTile(2,0));
        animatedTile.addTile(new StaticTile(3,0));
        animatedTile.addTile(new StaticTile(4,0));
        animatedTile.addTile(new StaticTile(3,0));
        spaceTiles[3] = animatedTile;
        spaceTiles[4] = new StaticTile(1,0);
        spaceTiles[5] = new StaticTile(1,0);
    }
    
    @Override
    public int getTileWidth() {
        return 24;
    }

    @Override
    public int getTileHeight() {
        return 24;
    }

    @Override
    public String getImageFile() {
        return "grid_tiles.png";
    }

    @Override
    public Tile getTile(Element e) {
        if (e instanceof Wall) {
            Wall wall = (Wall)e;
            int code = wall.getWallTypeId().getCode();
            if (code < wallTiles.length) {
                return wallTiles[code];
            }
        }
        if (e instanceof Space) {
            Space space = (Space)e;
            int code = space.getSpaceTypeId().getCode();
            if (code < spaceTiles.length) {
                return spaceTiles[code];
            }
        }
        return spaceTiles[0];
    }
    
}
