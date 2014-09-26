/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GEngine.TiledMap;

import GEngine.Graphics.Graphics2D.Color;
import GEngine.Graphics.Graphics2D.SpriteBatch;
import GEngine.TiledMap.Core.Map;
import static GEngine.TiledMap.Core.ObjectLayer.DEBUG_COLOR;
import GEngine.TiledMap.Core.Tile;
import GEngine.TiledMap.Core.TileLayer;
import GEngine.TiledMap.Render.MapRenderer;
import GEngine.TiledMap.Render.Renderer;
import GEngine.TiledMap.io.TMXReader;
import java.util.Properties;
import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;

/**
 *
 * @author 111
 */
public class TiledMap
{
    private static boolean DEBUG_DARW = true;
    
    public static void setDEBUG_DARW(boolean DEBUG_DARW)
    {
        TiledMap.DEBUG_DARW = DEBUG_DARW;
    }
    
    public static boolean debugDraw()
    {
        return TiledMap.DEBUG_DARW;
    }
    
    Map map;
    MapRenderer renderer;
    Rectangle clip;
    
    public TiledMap()
    {
    }
    
    public TiledMap(String mapFile, String resBaseDir)
    {
       TMXReader tr = new TMXReader(resBaseDir);
       map = tr.readMap(mapFile);
       renderer = new Renderer(map);
       clip = new Rectangle(map.getMapX(), map.getMapY(),
               map.getWidthInPixels(), map.getHeightInPixels());
    }
    
    public void draw(SpriteBatch batch,int x, int y)
    {
        this.draw(batch, x, y, -1, -1, -1);
    }

    public void draw(SpriteBatch batch, int layer)
    {
        this.draw(batch,0,0, -1, -1, layer);
    }
    
    public void draw(SpriteBatch batch, int x, int y,int layer)
    {
        this.draw(batch, x, y, -1, -1, layer);
    }
    
    public void draw(SpriteBatch batch, int x, int y, int width, int height)
    {
        this.draw(batch,x,y,width, height, -1);
    }
    
    public void draw(SpriteBatch batch, int x, int y, int width, int height, int layer)
    {
         renderer.paintTileLayer(batch, x, y,  width, height, layer, clip);
    }
    
    public void setDebugColor(Color color)
    {
        DEBUG_COLOR = color;
    }
    
    public Map getMap()
    {
        return map;
    }
    
    public void setClip(int x, int y, int w,int h)
    {
        this.clip = new Rectangle(x,y,w,h);
    }
    public void setClip(Rectangle clip)
    {
        this.clip = new Rectangle(clip);
    }
    
    public Properties getTilePropsAtPoint(int x, int y, int layer)
    {
        TileLayer t = (TileLayer) map.getLayer(layer);
        return t.getTileAtPoint(x, y).getProperties();
    }
    public Point getTilePointAtPos(float x, float y,int layer)
    {
        TileLayer t = (TileLayer) map.getLayer(layer);
        return t.getTilePointAtPos(x, y);
    }
    public int getTileIdAtPoint(int x, int y, int layer)
    {
        TileLayer t = (TileLayer) map.getLayer(layer);
        return t.getTileAtPoint(x, y).getId();
    }
    public Tile getTileAtPoint(int x, int y, int layer)
    {
        TileLayer t = (TileLayer) map.getLayer(layer);
        return t.getTileAtPoint(x, y);
    }
    public int getTileIdAt(int x, int y, int layer)
    {
        TileLayer t = (TileLayer) map.getLayer(layer);
        return t.getTileAt(x, y).getId();
    }
    public Properties getTilePropsAt(int x, int y, int layer)
    {
        TileLayer t = (TileLayer) map.getLayer(layer);
        return t.getTileAt(x, y).getProperties();
    }
    public Tile getTileAt(int x, int y, int layer)
    {
        TileLayer t = (TileLayer) map.getLayer(layer);
        return t.getTileAt(x, y);
    }


    
    
    
}
