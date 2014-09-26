/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GEngine.TiledMap.Core;

import GEngine.Graphics.Graphics2D.SpriteBatch;
import java.util.Properties;
import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;

/**
 *
 * @author 111
 */
public abstract class MapLayer {
    
    /** MIRROR_HORIZONTAL */
    public static final int MIRROR_HORIZONTAL = 1;
    /** MIRROR_VERTICAL */
    public static final int MIRROR_VERTICAL   = 2;

    /** ROTATE_90 */
    public static final int ROTATE_90  = 90;
    /** ROTATE_180 */
    public static final int ROTATE_180 = 180;
    /** ROTATE_270 */
    public static final int ROTATE_270 = 270;

    protected String name;
    protected boolean isVisible = true;
    protected float opacity = 1.0f;
    protected Rectangle bounds;
    private Properties properties = new Properties();
    protected Map myMap;//??
     
   public MapLayer() {
        bounds = new Rectangle();
        setMap(null);
    }

    /**
     * @param w width in tiles
     * @param h height in tiles
     */
    public MapLayer(int w, int h) {
        this(new Rectangle(0, 0, w, h));
    }

    public MapLayer(Rectangle r) {
        this();
        setBounds(r);
    }

    /**
     * @param map the map this layer is part of
     */
    MapLayer(Map map) {
        this();
        setMap(map);
    }

    /**
     * @param map the map this layer is part of
     * @param w width in tiles
     * @param h height in tiles
     */
    public MapLayer(Map map, int w, int h) {
        this(w, h);
        setMap(map);
    }

    /**
     * Performs a linear translation of this layer by (<i>dx, dy</i>).
     *
     * @param dx distance over x axis
     * @param dy distance over y axis
     */
    public void translate(int dx, int dy) {
        bounds.setX(dx);
        bounds.setY(dy);
    }

    public abstract void rotate(int angle);

    public abstract void mirror(int dir);

    /**
     * Sets the bounds (in tiles) to the specified Rectangle.
     *
     * @param bounds
     */
    public void setBounds(Rectangle bounds) {
        this.bounds = new Rectangle(bounds);
    }

    /**
     * Sets the name of this layer.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the map this layer is part of.
     *
     * @param map the Map object
     */
    public final void setMap(Map map) {
        myMap = map;
    }

    public Map getMap() {
        return myMap;
    }

    /**
     * Sets layer opacity. If it is different from the previous value and the
     * layer is visible, a MapChangedEvent is fired.
     *
     * @param opacity the new opacity for this layer
     */
    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

    /**
     * Sets the visibility of this map layer. If it changes from its current
     * value, a MapChangedEvent is fired.
     *
     * @param visible <code>true</code> to make the layer visible;
     *                <code>false</code> to make it invisible
     */
    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    /**
     * Sets the offset of this map layer. The offset is a distance by which to
     * shift this layer from the origin of the map.
     *
     * @param xOff x offset in tiles
     * @param yOff y offset in tiles
     */
    public void setOffset(int xOff, int yOff) {
        bounds.setX(xOff);
        bounds.setY(yOff);
    }

    /**
     * Returns the name of this layer.
     * @return the name of the layer
     */
    public String getName() {
        return name;
    }

    /**
     * Returns layer width in tiles.
     * @return layer width in tiles.
     */
    public int getWidth() {
        return bounds.getWidth();
    }

    /**
     * Returns layer height in tiles.
     * @return layer height in tiles.
     */
    public int getHeight() {
        return bounds.getHeight();
    }

    /**
     * Returns the layer bounds in tiles.
     * @return the layer bounds in tiles
     */
    public Rectangle getBounds() {
        return new Rectangle(bounds);
    }
    
  
    /**
     * Assigns the layer bounds in tiles to the given rectangle.
     * @param rect the rectangle to which the layer bounds are assigned
     */
    public void getBounds(Rectangle rect) {
        rect.setBounds(bounds);
    }

    /**
     * A convenience method to check if a point in tile-space is within
     * the layer boundaries.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @return <code>true</code> if the point (x,y) is within the layer
     *         boundaries, <code>false</code> otherwise.
     */
    public boolean contains(int x, int y) {
        return bounds.contains(x, y);
    }

    /**
     * Returns layer opacity.
     *
     * @return layer opacity, ranging from 0.0 to 1.0
     */
    public float getOpacity() {
        return opacity;
    }

    /**
     * Returns whether this layer is visible.
     *
     * @return <code>true</code> if the layer is visible, <code>false</code>
     * otherwise.
     */
    public boolean isVisible() {
        return isVisible;
    }
    
    public abstract boolean isEmpty();
    public abstract void resize(int width, int height, int dx, int dy);
    
    public void setProperties(Properties p) {
        properties.clear();
        properties.putAll(p);
    }

    public Properties getProperties() {
        return properties;
    }
    
    public abstract void render(SpriteBatch g, int x, int y, int width, int height, Rectangle clip);
    public abstract void updateObjectsPosition(int x, int y);
    public abstract  Point getTilePosAtPoint(int px, int py);
    public abstract Point getTilePointAtPos(float px, float py);
}
