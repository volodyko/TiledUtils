/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GEngine.TiledMap.Core;

import GEngine.Graphics.Graphics2D.Color;
import GEngine.Graphics.Graphics2D.SpriteBatch;
import GEngine.TiledMap.TiledMap;
import java.util.Iterator;
import java.util.LinkedList;
import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;

/**
 *
 * @author 111
 */
public class ObjectLayer extends MapLayer{
    private final LinkedList<MapObject> objects = new LinkedList<>();
    private static boolean DEBUG_DRAW = false;
    /**
     * Default constructor.
     */
    public ObjectLayer() {
    }

    /**
     * @param map    the map this object group is part of
     */
    public ObjectLayer(Map map) {
        super(map);
    }
    
    /**
     * Creates an object group that is part of the given map and has the given
     * origin.
     *
     * @param map    the map this object group is part of
     * @param origX  the x origin of this layer
     * @param origY  the y origin of this layer
     */
    public ObjectLayer(Map map, int origX, int origY) {
        super(map);
        setBounds(new Rectangle(origX, origY, 0, 0));
    }

    /**
     * Creates an object group with a given area. The size of area is
     * irrelevant, just its origin.
     *
     * @param area the area of the object group
     */
    public ObjectLayer(Rectangle area) {
        super(area);
    }

    /**
     * @see MapLayer#rotate(int)
     */
    @Override
    public void rotate(int angle) {
        // TODO: Implement rotating an object group
    }

    /**
     * @see MapLayer#mirror(int)
     */
    @Override
    public void mirror(int dir) {
        // TODO: Implement mirroring an object group
    }

    /**
     * @see MapLayer#resize(int,int,int,int)
     */
    @Override
    public void resize(int width, int height, int dx, int dy) {
        // TODO: Translate contained objects by the change of origin
    }

    @Override
    public boolean isEmpty() {
        return objects.isEmpty();
    }
    
    public void addObject(MapObject o) {
        objects.add(o);
        o.setObjectGroup(this);
    }

    public void removeObject(MapObject o) {
        objects.remove(o);
        o.setObjectGroup(null);
    }
    
    public Iterator<MapObject> getObjects() {
        return objects.iterator();
    }
    
    public MapObject getObjectAt(int x, int y) {
        for (MapObject obj : objects) {
            // Attempt to get an object bordering the point that has no width
            if (obj.getWidth() == 0 && obj.getX() + bounds.getX() == x) {
                return obj;
            }

            // Attempt to get an object bordering the point that has no height
            if (obj.getHeight() == 0 && obj.getY() + bounds.getY() == y) {
                return obj;
            }

            Rectangle rect = new Rectangle(obj.getX() + bounds.getX() * myMap.getTileWidth(),
                    obj.getY() + bounds.getY() * myMap.getTileHeight(),
                    obj.getWidth(), obj.getHeight());
            if (rect.contains(x, y)) {
                return obj;
            }
        }
        return null;
    }
    
   
    
    // This method will work at any zoom level, provided you provide the correct zoom factor. It also adds a one pixel buffer (that doesn't change with zoom).
    public MapObject getObjectNear(int x, int y, int zoom) {
        Rectangle mouse = new Rectangle(x - zoom - 1, y - zoom - 1, 2 * zoom + 1, 2 * zoom + 1);
        Rectangle shape;

        for (MapObject obj : objects) {
            if (obj.getWidth() == 0 && obj.getHeight() == 0) {
                shape = new Rectangle(obj.getX() * zoom, obj.getY() * zoom, 10 * zoom, 10 * zoom);
            } else {
                shape = new Rectangle(obj.getX() + bounds.getX() * myMap.getTileWidth(),
                        obj.getY() + bounds.getY() * myMap.getTileHeight(),
                        obj.getWidth() > 0 ? obj.getWidth() : zoom,
                        obj.getHeight() > 0 ? obj.getHeight() : zoom);
            }

            if (shape.intersects(mouse)) {
                return obj;
            }
        }

        return null;
    }
    
    @Override
    public void updateObjectsPosition(int x, int y)
    {
        for (MapObject obj : objects) 
        {
            int objx = obj.getX();
            int objy = obj.getY();
            
             
            double d = Math.sqrt((x - oldx)*(x - oldx) +  (y - oldy)*(y - oldy));
            
            
            if(x<oldx)
                obj.setX((int) (objx-d));
            if(x>oldx)
                obj.setX((int) (objx+d));
            
            if(y<oldy)
                obj.setY((int) (objy-d));
            if(y>oldy)  
                obj.setY((int) (objy+d));
            
           
        }
         oldx = x;
         oldy = y;
    }
    int oldx, oldy=0;
    

    @Override
    public void render(SpriteBatch g, int x, int y, int width, int height, Rectangle clip)
    {
       this.renderLayer(g,x,y);
    }
    
    public static Color DEBUG_COLOR = Color.WHITE;
    
    private void renderLayer(SpriteBatch batch, int x, int y)
    {
         if(TiledMap.debugDraw())
        {
            for (MapObject obj : objects) 
            {
               
                batch.setColor( DEBUG_COLOR);
                batch.drawRect(obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight(), 1);
                batch.setColor( Color.WHITE);
            }
        }
    }

    @Override
    public Point getTilePosAtPoint(int px, int py)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Point getTilePointAtPos(float px, float py)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
