/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GEngine.TiledMap.Core;

/**
 *
 * @author 111
 */
public class AnimatedTile extends Tile {
    
    private TiledSprite sprite;

    public AnimatedTile() {
    }

    public AnimatedTile(TileSet set) {
        super(set);
    }

    public AnimatedTile(Tile[] frames) {
        this();
        sprite = new TiledSprite(frames);
    }

    public AnimatedTile(TiledSprite s) {
        this();
        setSprite(s);
    }

    public void setSprite(TiledSprite s) {
        sprite = s;
    }

    public int countAnimationFrames() {
        return sprite.getTotalFrames();
    }

    public int countKeys() {
        return sprite.getTotalKeys();
    }

    public TiledSprite getSprite() {
        return sprite;
    }
}
