/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GEngine.TiledMap.Render;

import GEngine.Graphics.Graphics2D.SpriteBatch;
import org.lwjgl.util.Dimension;
import org.lwjgl.util.Rectangle;

/**
 *
 * @author 111
 */
/**
 * An interface defining methods to render a map.
 */
public interface MapRenderer
{
    /**
     * Calculates the dimensions of the map this renderer applies to.
     *
     * @return the dimensions of the given map in pixels
     */
    public Dimension getMapSize();


    public void paintTileLayer(SpriteBatch g, int x, int y,
                                              int width, int height, int l, Rectangle clip);

    
}
   
    

