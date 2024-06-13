package entities;

import graphics.Spritesheet;
import main.Game;
import world.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static graphics.Spritesheet.treeIdle;
import static graphics.Spritesheet.treeSpritesheet;

public class Tree extends Entity{
    public static boolean colliding = false;

    public Tree(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
    }

    public void tick(){
        if(placeMeeting(x, y, Game.entities)){
            Game.player.canMove = false;
        }else{
            Game.player.canMove = false;
        }
    }

    public void render(Graphics g){
        g.drawImage(Spritesheet.getSprite(treeSpritesheet, 0, 0, 112, 174), (this.getX() - Camera.x), (this.getY() - Camera.y) - 64, null);
    }
}
