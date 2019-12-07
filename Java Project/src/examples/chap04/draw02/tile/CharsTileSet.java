/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap04.draw02.tile;

import examples.chap04.draw02.state.Element;
import examples.chap04.draw02.state.Ghost;
import examples.chap04.draw02.state.GhostStatus;
import examples.chap04.draw02.state.Pacman;

public class CharsTileSet implements TileSet {

    private final Tile[] otherTiles;
    private final Tile[] pacmanTiles;
    private final Tile[][] ghostsTiles;
    
    public CharsTileSet() {
        otherTiles = new Tile[1];
        otherTiles[0] = new Tile(0,0);
        
        pacmanTiles = new Tile[5];
        pacmanTiles[0] = new Tile(6,1);
        pacmanTiles[1] = new Tile(3,1);
        pacmanTiles[2] = new Tile(7,1);
        pacmanTiles[3] = new Tile(6,1);
        pacmanTiles[4] = new Tile(2,1);      
        
        ghostsTiles = new Tile[5][5];
        for (int i=0;i<5;i++) {
            ghostsTiles[i][0] = new Tile(0,4+i);
            ghostsTiles[i][1] = new Tile(6,4+i);
            ghostsTiles[i][2] = new Tile(2,4+i);
            ghostsTiles[i][3] = new Tile(0,4+i);
            ghostsTiles[i][4] = new Tile(4,4+i);
        }
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
        return "chars_tiles.png";
    }

    @Override
    public Tile getTile(Element e) {
        if (e instanceof Pacman) {
            Pacman pacman = (Pacman)e;
            int code = pacman.getDirection().getCode();
            if (code < pacmanTiles.length) {
                return pacmanTiles[code];
            }
        }
        else if (e instanceof Ghost) {
            Ghost ghost = (Ghost)e;
            Tile[] tiles = null;
            if (ghost.getStatus() == GhostStatus.EYES) {
                tiles = ghostsTiles[4];
            }
            else {
                int color = ghost.getColor();
                if (color < ghostsTiles.length)
                    tiles = ghostsTiles[color];
            }
            if (tiles != null) {
                int code = ghost.getDirection().getCode();
                if (code < tiles.length) {
                    return tiles[code];
                }
            }
        }
        return otherTiles[0];
    }
    
}
