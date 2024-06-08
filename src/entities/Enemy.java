package entities;

import graphics.Spritesheet;

import java.awt.image.BufferedImage;

public class Enemy extends Entity{
    public static BufferedImage slimeSprite = Spritesheet.getSprite(Spritesheet.enemySpritesheet, 0, 0, 192, 192);
    public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
    }
}
