package world;

import graphics.Spritesheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile extends Rectangle{
    public static BufferedImage TILE_FLOOR = Spritesheet.getSprite(Spritesheet.tileSpritesheet, 513, 64, 64,64);
    public static BufferedImage TILE_WALL = Spritesheet.getSprite(Spritesheet.tileSpritesheet, 257, 64, 64,64);
    public static BufferedImage WATER = Spritesheet.getSprite(Spritesheet.water, 0, 0, 64, 64);

    private BufferedImage sprite;
    private int x, y;

    public Tile(int x, int y, BufferedImage sprite){
        super(x, y, 64, 64);
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

    public void render(Graphics g){
        g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
    }
}
