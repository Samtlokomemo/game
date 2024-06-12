package entities;

import main.Game;
import world.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static graphics.Spritesheet.treeIdle;

public class Tree extends Entity{
    public static boolean colliding = false;

    public Tree(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        this.targetFrames = 5;
        this.animation = treeIdle;
    }

    public void tick(){
        if(placeMeeting(x, y, Game.entities)){
            Game.player.canMove = false;
        }else{
            Game.player.canMove = false;
        }

        if(this.curFrames == this.targetFrames){
            this.curFrames = 0;
            this.curAnimation++;
            if(this.curAnimation == this.animation.length){
                this.curAnimation = 0;
            }
        }else{
            this.curFrames++;
        }
    }

    public void render(Graphics g){
        g.drawImage(this.animation[this.curAnimation], (this.getX() - Camera.x), (this.getY() - Camera.y) - 64, null);
    }
}
