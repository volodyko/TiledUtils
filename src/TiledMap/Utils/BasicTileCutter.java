/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GEngine.TiledMap.Utils;

import GEngine.Core.Utils.ImageHelper;
import GEngine.Graphics.Graphics2D.Texture2D;
import java.awt.image.BufferedImage;
import org.lwjgl.util.Dimension;

/**
 *
 * @author 111
 */
public class BasicTileCutter implements TileCutter {
    
    private int nextX, nextY;
    private BufferedImage image;
    private final int tileWidth;
    private final int tileHeight;
    private final int tileSpacing;
    private final int tileMargin;
    
    public BasicTileCutter(int tileWidth, int tileHeight, int tileSpacing, int tileMargin) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.tileSpacing = tileSpacing;
        this.tileMargin = tileMargin;

        this.reset();
    }
    
    @Override
    public String getName() {
        return "Basic";
    }

    @Override
    public void setImage(BufferedImage image) {
        this.image = image;
    }
    
    @Override
    public Texture2D getNextTile() {
        if (nextY + tileHeight + tileMargin <= image.getHeight()) {
            BufferedImage tile =
                image.getSubimage(nextX, nextY, tileWidth, tileHeight);
            nextX += tileWidth + tileSpacing;

            if (nextX + tileWidth + tileMargin > image.getWidth()) {
                nextX = tileMargin;
                nextY += tileHeight + tileSpacing;
            }
            
            return ImageHelper.BufferedImgToTexture(tile);
        }

        return null;
    }

    @Override
    public final void reset() {
        nextX = tileMargin;
        nextY = tileMargin;
    }
    
    @Override
    public Dimension getTileDimensions() {
        return new Dimension(tileWidth, tileHeight);
    }
    
    /**
     * Returns the spacing between tile images.
     * @return the spacing between tile images.
     */
    public int getTileSpacing() {
        return tileSpacing;
    }

    /**
     * Returns the margin around the tile images.
     * @return the margin around the tile images.
     */
    public int getTileMargin() {
        return tileMargin;
    }

    /**
     * Returns the number of tiles per row in the tileset image.
     * @return the number of tiles per row in the tileset image.
     */
    public int getTilesPerRow() {
        return (image.getWidth() - 2 * tileMargin + tileSpacing) /
                (tileWidth + tileSpacing);
    }
    
  
}
