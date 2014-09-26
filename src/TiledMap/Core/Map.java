/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GEngine.TiledMap.Core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import org.lwjgl.util.Rectangle;

/**
 *
 * @author 111
 */
public class Map implements Iterable<MapLayer>{
    
    
    public static final int ORIENTATION_ORTHOGONAL = 1;
    public static final int ORIENTATION_ISOMETRIC = 2;
    public static final int ORIENTATION_HEXAGONAL = 4;
    /** Shifted (used for iso and hex). */
    public static final int ORIENTATION_SHIFTED = 5;
    
    private int orientation = ORIENTATION_ORTHOGONAL;
    private String fileName;
    
    private  ArrayList<TileSet> tileSets;
    private  ArrayList<MapLayer> layers;
    private Properties properties;
    private int width;
    private int height;
    private int tileWidth;
    private int tileHeight;
    protected Rectangle bounds;
    
    private int mapX = 0;
    private int mapY = 0;
  
    
    public Map(int width, int height)
    {
       
                
        this.layers = new ArrayList<>();
        this.tileSets = new ArrayList<>();
        bounds = new Rectangle(0,0,width, height);
        this.width = width;
        this.height = height;
        properties = new Properties();
    }
    public Map(String mapFile) {
        this.layers = new ArrayList<>();
        this.tileSets = new ArrayList<>();
        
    }

    public int getMapX()
    {
        return mapX;
    }

    public void setMapX(int mapX)
    {
        this.mapX = mapX;
    }

    public int getMapY()
    {
        return mapY;
    }

    public void setMapY(int mapY)
    {
        this.mapY = mapY;
    }

    
    
       
    /**
     * Returns the total number of layers.
     *
     * @return the size of the layer vector
     */
    public int getLayerCount() {
        return layers.size();
    }

     /**
     * Changes the bounds of this plane to include all layers completely.
     */
    public void fitBoundsToLayers() {
        int _width = 0;
        int _height = 0;

        Rectangle layerBounds = new Rectangle();

        for (int i = 0; i < layers.size(); i++) {
            getLayer(i).getBounds(layerBounds);
            if (_width < layerBounds.getWidth()) _width = layerBounds.getWidth();
            if (_height < layerBounds.getHeight()) _height = layerBounds.getHeight();
        }

        bounds.setWidth(_width); 
        bounds.setHeight(_height); 
    }
    
    /**
     * Resizes this plane. The (dx, dy) pair determines where the original
     * plane should be positioned on the new area. Only layers that exactly
     * match the bounds of the map are resized, any other layers are moved by
     * the given shift.
     *
     * @see tiled.core.MapLayer#resize
     *
     * @param width  The new width of the map.
     * @param height The new height of the map.
     * @param dx     The shift in x direction in tiles.
     * @param dy     The shift in y direction in tiles.
     */
    public void resize(int width, int height, int dx, int dy) {
        for (MapLayer layer : layers) {
            if (layer.bounds.equals(bounds)) {
                layer.resize(width, height, dx, dy);
            } else {
                layer.setOffset(layer.bounds.getX() + dx, layer.bounds.getY() + dy);
            }
        }
        bounds.setWidth(width); 
        bounds.setHeight(height);
    }
    
    /**
     * Determines whether the point (x,y) falls within the plane.
     *
     * @param x
     * @param y
     * @return <code>true</code> if the point is within the plane,
     *         <code>false</code> otherwise
     */
    public boolean inBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < bounds.getWidth() && y < bounds.getHeight();
    }
    
    @Override
    public Iterator<MapLayer> iterator() {
        return layers.iterator();
    }
    
     public void addTileset(TileSet tileset) {
        if (tileset == null || tileSets.indexOf(tileset) > -1) {
            return;
        }

        Tile t = tileset.getTile(0);

        if (t != null) {
            int tw = t.getWidth();
            int th = t.getHeight();
            if (tw != tileWidth) {
                if (tileWidth == 0) {
                    tileWidth = tw;
                    tileHeight = th;
                }
            }
        }

        tileSets.add(tileset);
    }

    /**
     * Removes a {@link TileSet} from the map, and removes any tiles in the set
     * from the map layers.
     *
     * @param tileset TileSet to remove
     */
    public void removeTileset(TileSet tileset) {
        // Sanity check
        final int tilesetIndex = tileSets.indexOf(tileset);
        if (tilesetIndex == -1)
            return;

        // Go through the map and remove any instances of the tiles in the set
        for (Tile tile : tileset) {
            for (MapLayer ml : layers) {
                if (ml instanceof TileLayer) {
                    ((TileLayer) ml).removeTile(tile);
                }
            }
        }

        tileSets.remove(tileset);
    }

     /**
     * Returns a <code>Rectangle</code> representing the maximum bounds in
     * tiles.
     * @return a new rectangle containing the maximum bounds of this plane
     */
    public Rectangle getBounds() {
        return new Rectangle(bounds);
    }
    public void setBounds(Rectangle newBounds)
    {
        this.bounds = newBounds;
    }
    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getTileWidth() {
       return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public void setTileWidth(int tileWidth) {
        this.tileWidth = tileWidth;
    }

    public void setTileHeight(int tileHeight) {
        this.tileHeight = tileHeight;
    }

    public Properties getProperties() {
        return properties;
    }
    
    public void setFilename(String filename) {
        fileName = filename;
    }
    
    public MapLayer addLayer(MapLayer layer) {
        layer.setMap(this);
        layers.add(layer);
        return layer;
    }
    
    
    /**
     * Returns the maximum tile height. This is the height of the highest tile
     * in all tileSets or the tile height used by this map if it's smaller.
     *
     * @return int The maximum tile height
     */
    public int getTileHeightMax() {
        int maxHeight = tileHeight;

        for (TileSet tileset : tileSets) {
            int _height = tileset.getTileHeight();
            if (_height > maxHeight) {
                maxHeight = _height;
            }
        }

        return maxHeight;
    }

    /**
     * Swaps the tile sets at the given indices.
     * @param index0
     * @param index1
     */
    public void swapTileSets(int index0, int index1) {
        if (index0 == index1) return;
        TileSet set = tileSets.get(index0);
        tileSets.set(index0, tileSets.get(index1));
        tileSets.set(index1, set);
    }

    /**
     * Returns the orientation of this map. Orientation will be one of
     * {@link Map#ORIENTATION_ISOMETRIC}, {@link Map#ORIENTATION_ORTHOGONAL},
     * {@link Map#ORIENTATION_HEXAGONAL} and {@link Map#ORIENTATION_SHIFTED}.
     *
     * @return The orientation from the enumerated set
     */
    public int getOrientation() {
        return orientation;
    }

    /**
     * Returns string describing the map. The form is <code>Map[width x height
     * x layers][tileWidth x tileHeight]</code>, for example <code>
     * Map[64x64x2][24x24]</code>.
     *
     * @return string describing map
     */
    @Override
    public String toString() {
        return "Map[" + bounds.getWidth() + "x" + bounds.getHeight() + "x" +
            getLayerCount() + "][" + tileWidth + "x" +
            tileHeight + "]";
    }
    
    public void setLayer(int index, MapLayer layer) {
        layer.setMap(this);
        layers.set(index, layer);
    }
    
    public void insertLayer(int index, MapLayer layer) {
        layer.setMap(this);
        layers.add(index, layer);
    }
    
    /**
     * Removes the layer at the specified index. Layers above this layer will
     * move down to fill the gap.
     *
     * @param index the index of the layer to be removed
     * @return the layer that was removed from the list
     */
    public MapLayer removeLayer(int index) {
        return layers.remove(index);
    }

    /**
     * Removes all layers from the plane.
     */
    public void removeAllLayers() {
        layers.clear();
    }
    
      /**
     * Returns the layer list.
     *
     * @return Vector the layer vector
     */
    public ArrayList<MapLayer> getLayers() {
        return layers;
    }
    
    /**
     * Returns a vector with the currently loaded tileSets.
     *
     * @return Vector
     */
    public ArrayList<TileSet> getTileSets() {
        return tileSets;
    }
    
    /**
     * Sets the layer vector to the given java.util.Vector.
     *
     * @param layers the new set of layers
     */
    public void setLayers(ArrayList<MapLayer> layers) {
        this.layers = layers;
    }
    
    /**
     * Returns the layer at the specified vector index.
     *
     * @param i the index of the layer to return
     * @return the layer at the specified index, or null if the index is out of
     *         bounds
     */
    public MapLayer getLayer(int i) {
        try {
            return layers.get(i);
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        return null;
    }
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public int getHeightInPixels()
    {
        return height * tileHeight;
    }
    public int getWidthInPixels()
    {
        return width * tileWidth;
    }
    
    
}
