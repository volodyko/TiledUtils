/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GEngine.TiledMap.Core;

import GEngine.Graphics.Graphics2D.Texture2D;
import java.util.Properties;

/**
 *
 * @author 111
 */
public class Tile{
     private int id;
     private Texture2D image;
     private TileSet tileSet;
     private Properties properties;
     
    public Tile()
    {
         properties = new Properties();
    }
    
    public Tile(TileSet set) {
        this();
        setTileSet(set);
    }
    
     public void setId(int i) {
        if (i >= 0) {
            id = i;
        }
    }
     
    public int getWidth() {
        if (image != null)
            return image.getWidth();
        return 0;
    }

    public int getHeight() {
        if (image != null)
            return image.getHeight();
        return 0;
    } 
    
    public Texture2D getImage() {
        return image;
    }
   
    public void setImage(Texture2D img) {
        image = img;
    }

    public void setTileSet(TileSet set) {
        tileSet = set;
    }
    /**
     * Returns the {@link tiled.core.TileSet} that this tile is part of.
     *
     * @return TileSet
     */
    public TileSet getTileSet() {
        return tileSet;
    }
    
    public int getId() {
        return id;
    }
    
    public Properties getProperties() {
        return properties;
    }
    
    public void setProperties(Properties _properties) {
        this.properties = _properties;
    }

    /**
     * @return 
     * @see java.lang.Object#toString()
     */
    
    public String toString() {
        return "Tile " + id + " (" + getWidth() + "x" + getHeight() + ")";
    }
     
     
    
}
