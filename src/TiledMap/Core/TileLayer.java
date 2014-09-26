/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GEngine.TiledMap.Core;

import GEngine.Graphics.Graphics2D.SpriteBatch;
import GEngine.Graphics.Graphics2D.Texture2D;
import java.util.HashMap;
import java.util.Properties;
import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;

/**
 *
 * @author 111
 */
public class TileLayer extends MapLayer{
     protected Tile[][] map;
     protected HashMap<Object, Properties> tileInstanceProperties = new HashMap<>();
     
     
     public Properties getTileInstancePropertiesAt(int x, int y) {
        if (!bounds.contains(x, y)) {
            return null;
        }
        Object key = new Point(x, y);
        return tileInstanceProperties.get(key);
    }

    public void setTileInstancePropertiesAt(int x, int y, Properties tip) {
        if (bounds.contains(x, y)) {
            Object key = new Point(x, y);
            tileInstanceProperties.put(key, tip);
        }
    }

     /**
     * Default constructor.
     */
    public TileLayer() {
    }

    /**
     * Construct a TileLayer from the given width and height.
     *
     * @param w width in tiles
     * @param h height in tiles
     */
    public TileLayer(int w, int h) {
        super(w, h);
    }

    /**
     * Create a tile layer using the given bounds.
     *
     * @param r the bounds of the tile layer.
     */
    public TileLayer(Rectangle r) {
        super(r);
    }

    /**
     * @param m the map this layer is part of
     */
    TileLayer(Map m) {
        super(m);
    }

    /**
     * @param m the map this layer is part of
     * @param w width in tiles
     * @param h height in tiles
     */
    public TileLayer(Map m, int w, int h) {
        super(w, h);
        setMap(m);
    }

    /**
     * Rotates the layer by the given Euler angle.
     *
     * @param angle The Euler angle (0-360) to rotate the layer array data by.
     * @see MapLayer#rotate(int)
     */
     @Override
    public void rotate(int angle) {
        Tile[][] trans;
        int xtrans = 0, ytrans = 0;

        switch (angle) {
            case ROTATE_90:
                trans = new Tile[bounds.getWidth()][bounds.getHeight()];
                xtrans = bounds.getHeight() - 1;
                break;
            case ROTATE_180:
                trans = new Tile[bounds.getHeight()][bounds.getWidth()];
                xtrans = bounds.getWidth() - 1;
                ytrans = bounds.getHeight() - 1;
                break;
            case ROTATE_270:
                trans = new Tile[bounds.getWidth()][bounds.getHeight()];
                ytrans = bounds.getWidth() - 1;
                break;
            default:
                System.out.println("Unsupported rotation (" + angle + ")");
                return;
        }

        double ra = Math.toRadians(angle);
        int cos_angle = (int)Math.round(Math.cos(ra));
        int sin_angle = (int)Math.round(Math.sin(ra));

        for (int y = 0; y < bounds.getHeight(); y++) {
            for (int x = 0; x < bounds.getWidth(); x++) {
                int xrot = x * cos_angle - y * sin_angle;
                int yrot = x * sin_angle + y * cos_angle;
                trans[yrot + ytrans][xrot + xtrans] = getTileAt(x+bounds.getX(), y+bounds.getY());
            }
        }

        bounds.setWidth(trans[0].length);
        bounds.setHeight(trans.length);
        map = trans;
    }

    /**
     * Performs a mirroring function on the layer data. Two orientations are
     * allowed: vertical and horizontal.
     *
     * Example: <code>layer.mirror(MapLayer.MIRROR_VERTICAL);</code> will
     * mirror the layer data around a horizontal axis.
     *
     * @param dir the axial orientation to mirror around
     */
     @Override
    public void mirror(int dir) {
        Tile[][] mirror = new Tile[bounds.getHeight()][bounds.getWidth()];
        for (int y = 0; y < bounds.getHeight(); y++) {
            for (int x = 0; x < bounds.getWidth(); x++) {
                if (dir == MIRROR_VERTICAL) {
                    mirror[y][x] = map[bounds.getHeight() - 1 - y][x];
                } else {
                    mirror[y][x] = map[y][bounds.getHeight() - 1 - x];
                }
            }
        }
        map = mirror;
    }
     /**
     * Checks to see if the given Tile is used anywhere in the layer.
     *
     * @param t a Tile object to check for
     * @return <code>true</code> if the Tile is used at least once,
     *         <code>false</code> otherwise.
     */
    public boolean isUsed(Tile t) {
        for (int y = 0; y < bounds.getHeight(); y++) {
            for (int x = 0; x < bounds.getWidth(); x++) {
                if (map[y][x] == t) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean isEmpty() {
        for (int p = 0; p < 2; p++) {
            for (int y = 0; y < bounds.getHeight(); y++) {
                for (int x = p; x < bounds.getWidth(); x += 2) {
                    if (map[y][x] != null)
                        return false;
                }
            }
        }
        return true;
    }
   
      /**
     * Returns the tile at the specified position.
     *
     * @param tx Tile-space x coordinate
     * @param ty Tile-space y coordinate
     * @return tile at position (tx, ty) or <code>null</code> when (tx, ty) is
     *         outside this layer
     */
    public Tile getTileAt(int tx, int ty) {
        return (bounds.contains(tx, ty)) ?
                map[ty - bounds.getY()][tx - bounds.getX()] : null;
    }
  
     
     public Tile getTileAtPoint(int px, int py)
     {   
             
         int xrow = ((bounds.getWidth()+px)/myMap.getTileWidth())-1;
         int yrow = ((bounds.getHeight()+py)/myMap.getTileHeight())-1;
         return (bounds.contains(xrow, yrow)) ?
                map[yrow - bounds.getY()][xrow - bounds.getX()] : null;
     }
     
     @Override
     public  Point getTilePosAtPoint(int px, int py)
     { 
         int xrow = ((bounds.getWidth()+px)/myMap.getTileWidth())-1;
         int yrow = ((bounds.getHeight()+py)/myMap.getTileHeight())-1;
         return new Point (xrow, yrow);
     }
     
     @Override
     public Point getTilePointAtPos(float px, float py)
     {
          int xrow = ((int)(bounds.getWidth()-px)*myMap.getTileWidth())-1;
         int yrow = ((int)(bounds.getHeight()-py)*myMap.getTileHeight())-1;
         return new Point(xrow, yrow);
     }
    /**
    * Removes any occurences of the given tile from this map layer. If layer
    * is locked, an exception is thrown.
    *
    * @param tile the Tile to be removed
    */
    public void removeTile(Tile tile) {
        for (int y = 0; y < bounds.getHeight(); y++) {
            for (int x = 0; x < bounds.getWidth(); x++) {
                if (map[y][x] == tile) {
                    setTileAt(x + bounds.getX(), y + bounds.getY(), null);
                }
            }
        }
    }

    /**
     * Sets the tile at the specified position. Does nothing if (tx, ty) falls
     * outside of this layer.
     *
     * @param tx x position of tile
     * @param ty y position of tile
     * @param ti the tile object to place
     */
    public void setTileAt(int tx, int ty, Tile ti) {
        if (bounds.contains(tx, ty)) {
            map[ty - bounds.getY()][tx - bounds.getX()] = ti;
        }
    }
    
    /**
     * Sets the bounds (in tiles) to the specified Rectangle. <b>Caution:</b>
     * this causes a reallocation of the data array, and all previous data is
     * lost.
     *
     * @param bounds new new bounds of this tile layer (in tiles)
     * @see MapLayer#setBounds
     */
    
     @Override
    public void setBounds(Rectangle bounds) {
        super.setBounds(bounds);
        map = new Tile[bounds.getHeight()][bounds.getWidth()];

        // Tile instance properties is null when this method is called from
        // the constructor of MapLayer
        if (tileInstanceProperties != null) {
            tileInstanceProperties.clear();
        }
    }
     
      /**
     * Returns the first occurrence (using top down, left to right search) of
     * the given tile.
     *
     * @param t the {@link Tile} to look for
     * @return A java.awt.Point instance of the first instance of t, or
     *         <code>null</code> if it is not found
     */
    public Point locationOf(Tile t) {
        for (int y = bounds.getY(); y < bounds.getHeight() + bounds.getY(); y++) {
            for (int x = bounds.getX(); x < bounds.getWidth() + bounds.getX(); x++) {
                if (getTileAt(x, y) == t) {
                    return new Point(x, y);
                }
            }
        }
        return null;
    }
    /**
     * Replaces all occurrences of the Tile <code>find</code> with the Tile
     * <code>replace</code> in the entire layer
     *
     * @param find    the tile to replace
     * @param replace the replacement tile
     */
    public void replaceTile(Tile find, Tile replace) {
        for (int y = bounds.getY(); y < bounds.getY() + bounds.getHeight(); y++) {
            for (int x = bounds.getX(); x < bounds.getX() + bounds.getWidth(); x++) {
                if(getTileAt(x,y) == find) {
                    setTileAt(x, y, replace);
                }
            }
        }
    }
    /**
     * @param width  the new width of the layer
     * @param height the new height of the layer
     * @param dx     the shift in x direction
     * @param dy     the shift in y direction
     */
     @Override
    public void resize(int width, int height, int dx, int dy) {
        Tile[][] newMap = new Tile[height][width];
        HashMap<Object, Properties> newTileInstanceProperties = new HashMap<>();

        int maxX = Math.min(width, bounds.getWidth() + dx);
        int maxY = Math.min(height, bounds.getHeight() + dy);

        for (int x = Math.max(0, dx); x < maxX; x++) {
            for (int y = Math.max(0, dy); y < maxY; y++) {
                newMap[y][x] = getTileAt(x - dx, y - dy);

                Properties tip = getTileInstancePropertiesAt(x - dx, y - dy);
                if (tip != null) {
                    newTileInstanceProperties.put(new Point(x, y), tip);
                }
            }
        }
        tileInstanceProperties = newTileInstanceProperties;
        map = newMap;
        bounds.setWidth(width);
        bounds.setHeight(height);
    }
       
     @Override
     public void render(SpriteBatch g, int x, int y,  int width, int height, Rectangle clip)
     {
        if(isEmpty())
            return;
        
        Point sxy = this.getTilePosAtPoint(clip.getX(), clip.getY());
       
        Point swh = this.getTilePosAtPoint(clip.getWidth(), clip.getHeight());
        
        for (int i =sxy.getX() ; i < swh.getX()+5; ++i) {
            for (  int j = sxy.getY(); j < swh.getY()+5; ++j) {
                
                final Tile tile = this.getTileAt(i, j);
                if (tile == null)
                    continue;     
                final Texture2D image = tile.getImage();    
                if (image == null)
                    continue;
              g.draw(image, x+(i * image.getWidth()),  y+((j + 1) * image.getHeight() - image.getHeight()));                 
            }
        }
     }

    @Override
    public void updateObjectsPosition(int x, int y)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
}
