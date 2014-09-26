/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GEngine.TiledMap.Core;


import GEngine.Core.Utils.ResourceLoader;
import GEngine.Graphics.Graphics2D.Texture2D;
import java.util.Properties;
import org.lwjgl.util.Rectangle;

/**
 *
 * @author 111
 */
public class MapObject {
    
    private Properties properties = new Properties();
    private ObjectLayer objectGroup;
    private Rectangle bounds = new Rectangle();
    private String name = "Object";
    private String type = "";
    private String imageSource = "";  
    private Texture2D image;
    
    
    public MapObject(int x, int y, int width, int height) {
        bounds = new Rectangle(x, y, width, height);
    }


    /**
     * @return the object group this object is part of
     */
    public ObjectLayer getObjectGroup() {
        return objectGroup;
    }
     /**
     * Sets the object group this object is part of. Should only be called by
     * the object group.
     *
     * @param objectGroup the object group this object is part of
     */
    void setObjectGroup(ObjectLayer object) {
        objectGroup = object;
    }

    public void setImageSource(String source) {
        if (imageSource.equals(source))
            return;

        imageSource = source;

        // Attempt to read the image
        if (imageSource.length() > 0) {
            image = new Texture2D(ResourceLoader.getResource(source));
        } else {
            image = null;
        }
    }
        
    public Texture2D getImage(double zoom) {
        if (image == null)
            return null;

        return image;
    }
    
    public int getX() {
        return bounds.getX();
    }

    public void setX(int x) {
        bounds.setX(x);
    }

    public int getY() {
        return bounds.getY();
    }

    public void setY(int y) {
        bounds.setY(y);
    }

    public void translate(int dx, int dy) {
        bounds.translate(dx, dy);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getWidth() {
        return bounds.getWidth();
    }

    public void setWidth(int width) {
        bounds.setWidth(width);
    }

    public void setHeight(int height) {
        bounds.setHeight(height);
    }

    public int getHeight() {
        return bounds.getHeight();
    }
    
    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public String getImageSource() {
        return imageSource;
    }
    
    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties p) {
        properties = p;
    }

    @Override
    public String toString() {
        return type + " (" + getX() + "," + getY() + ")";
    }
    
}
