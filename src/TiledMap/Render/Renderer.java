/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GEngine.TiledMap.Render;

import GEngine.Graphics.Graphics2D.SpriteBatch;
import GEngine.TiledMap.Core.Map;
import GEngine.TiledMap.Core.MapLayer;
import GEngine.TiledMap.Core.ObjectLayer;
import org.lwjgl.util.Dimension;
import org.lwjgl.util.Rectangle;

/**
 *
 * @author 111
 */
public class Renderer implements MapRenderer {

    private final Map map;
    
    public Renderer(Map map) {
        this.map = map;
    }
     
    @Override
    public Dimension getMapSize() {
        return new Dimension(
                map.getWidth() * map.getTileWidth(),
                map.getHeight() * map.getTileHeight());
    }
   
    public void updateObjects(int x, int y)
    {
         for(MapLayer og: map.getLayers())
            {
                if (og instanceof ObjectLayer )
                    og.updateObjectsPosition(x, y);
            }
            map.setMapX(x);
            map.setMapY(y);
    }
    	
    @Override
    public void paintTileLayer(SpriteBatch g, int x, int y, int width, int height, int l, Rectangle clip)
    {
        if(width < 0)
            width = map.getWidth();
        if(height< 0)
            height = map.getHeight();
        
        if(l >= 0)
        {
            map.getLayer(l).render(g, x, y,width,height,clip);
        }
        else
            for(MapLayer layer: map.getLayers())
            {
                layer.render(g, x, y,  width, height,clip);
                
            }       
        if(map.getMapX() != x || map.getMapY() != y)
        {         
           updateObjects(x,y);
           map.setMapX(x);
           map.setMapY(y);
        }
    }

    
  
    
}
