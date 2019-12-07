/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.immutable01.render;

import examples.chap05.immutable01.state.Element;
import examples.chap05.immutable01.state.Ghost;
import examples.chap05.immutable01.state.GhostStatus;
import examples.chap05.immutable01.state.Pacman;
import examples.chap05.immutable01.state.PacmanStatus;

public class CharsTileSet implements TileSet {

    private final Tile[] otherTiles;
    private final Tile[] pacmanTiles;
    private final Tile[] dyingTiles;
    private final Tile[][] ghostsTiles;
    private final Tile[] ghostFlee;
    
    public CharsTileSet() {
        otherTiles = new Tile[1];
        otherTiles[0] = new StaticTile(0,0);

        AnimatedTile animatedTile;
        pacmanTiles = new AnimatedTile[5];
        
        animatedTile = new AnimatedTile(6);
        animatedTile.addTile(new StaticTile(1,1));
        animatedTile.addTile(new StaticTile(3,1));
        animatedTile.addTile(new StaticTile(3,3));
        animatedTile.addTile(new StaticTile(3,1));
        pacmanTiles[1] = animatedTile;
        
        animatedTile = new AnimatedTile(6);
        animatedTile.addTile(new StaticTile(5,1));
        animatedTile.addTile(new StaticTile(7,1));
        animatedTile.addTile(new StaticTile(3,3));
        animatedTile.addTile(new StaticTile(7,1));
        pacmanTiles[2] = animatedTile;

        animatedTile = new AnimatedTile(6);
        animatedTile.addTile(new StaticTile(4,1));
        animatedTile.addTile(new StaticTile(6,1));
        animatedTile.addTile(new StaticTile(3,3));
        animatedTile.addTile(new StaticTile(6,1));
        pacmanTiles[3] = animatedTile;
        
        animatedTile = new AnimatedTile(6);
        animatedTile.addTile(new StaticTile(0,1));
        animatedTile.addTile(new StaticTile(2,1));
        animatedTile.addTile(new StaticTile(3,3));
        animatedTile.addTile(new StaticTile(2,1));
        pacmanTiles[4] = animatedTile;
     
        pacmanTiles[0] = pacmanTiles[3];
        
        dyingTiles = new Tile[12];
        dyingTiles[0] = new StaticTile(0,2);
        dyingTiles[1] = new StaticTile(1,2);
        dyingTiles[2] = new StaticTile(2,2);
        dyingTiles[3] = new StaticTile(3,2);
        dyingTiles[4] = new StaticTile(4,2);
        dyingTiles[5] = new StaticTile(5,2);
        dyingTiles[6] = new StaticTile(6,2);
        dyingTiles[7] = new StaticTile(7,2);
        dyingTiles[8] = new StaticTile(0,3);
        dyingTiles[9] = new StaticTile(1,3);
        dyingTiles[10] = new StaticTile(2,3);
        dyingTiles[11] = new StaticTile(1,0);
        
        ghostsTiles = new Tile[5][5];
        for (int i=0;i<5;i++) {
            
            animatedTile = new AnimatedTile(4);
            animatedTile.addTile(new StaticTile(6,4+i));
            animatedTile.addTile(new StaticTile(7,4+i));
            ghostsTiles[i][1] = animatedTile;

            animatedTile = new AnimatedTile(4);
            animatedTile.addTile(new StaticTile(2,4+i));
            animatedTile.addTile(new StaticTile(3,4+i));
            ghostsTiles[i][2] = animatedTile;

            animatedTile = new AnimatedTile(4);
            animatedTile.addTile(new StaticTile(0,4+i));
            animatedTile.addTile(new StaticTile(1,4+i));
            ghostsTiles[i][3] = animatedTile;
            
            animatedTile = new AnimatedTile(4);
            animatedTile.addTile(new StaticTile(4,4+i));
            animatedTile.addTile(new StaticTile(5,4+i));
            ghostsTiles[i][4] = animatedTile;
            
            ghostsTiles[i][0] = ghostsTiles[i][3];
        }

        ghostFlee = new Tile[2];
        animatedTile = new AnimatedTile(4);
        animatedTile.addTile(new StaticTile(6,3));
        animatedTile.addTile(new StaticTile(7,3));
        ghostFlee[0] = animatedTile;

        animatedTile = new AnimatedTile(4);
        animatedTile.addTile(new StaticTile(4,3));
        animatedTile.addTile(new StaticTile(5,3));
        ghostFlee[1] = animatedTile;
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
            if (pacman.getStatus() == PacmanStatus.DEAD) {
                int i = dyingTiles.length - pacman.getStatusTime();
                if (i >= dyingTiles.length) i = dyingTiles.length - 1;
                if (i < 0) i = 0;
                return dyingTiles[i];
            }
            int code = pacman.getDirection().getCode();
            if (code < pacmanTiles.length) {
                return pacmanTiles[code];
            }
        }
        else if (e instanceof Ghost) {
            Ghost ghost = (Ghost)e;
            if (ghost.getStatus() == GhostStatus.FLEE) {
                if (ghost.getStatusTime() < 20) {
                    return ghostFlee[ghost.getStatusTime()%2];
                }
                return ghostFlee[0];
            }
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
