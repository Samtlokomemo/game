package entities;

import graphics.Spritesheet;
import main.Game;
import world.Camera;
import world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{
    public int spd = 6;
    public boolean right, up, down, left;
    public int curAnimation = 0, curFrames = 0, targetFrames = 6;

    public static BufferedImage[] playerIdle, playerWalk;
    public static BufferedImage[] animation = playerIdle;
    public boolean moved = false;
    String direction;

    public Player(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);

        //Sprites
        playerIdle = new BufferedImage[6];
        for (int i = 0; i < 6; i++) {
            playerIdle[i] = Spritesheet.getSprite(Spritesheet.playerSpritesheet, i * 192, 0, 192, 192);
        }

        playerWalk = new BufferedImage[6];
        for (int i = 0; i < 6; i++) {
            playerWalk[i] = Spritesheet.getSprite(Spritesheet.playerSpritesheet, i * 192, 192, 192, 192);
        }
    }

    public void tick(){
        if(right && isFree(this.getX() + spd, this.getY())){
            x+=spd;
            direction = "right";
        } else if (left && isFree(this.getX() - spd, this.getY())) {
            x-=spd;
            direction = "left";
        }

        if (up && isFree(this.getX(), this.getY() - spd)){
            y-=spd;
        } else if (down && isFree(this.getX(), this.getY() + spd)) {
            y+=spd;
        }

        if(moved){
            animation = playerWalk;
        }else{
            animation = playerIdle;
        }

        //Animação
        curFrames++;
        if(curFrames == targetFrames){
            curFrames = 0;
            curAnimation++;
            if(curAnimation == animation.length){
                curAnimation = 0;
            }
        }

        Camera.x = Camera.clamp(this.getX() - (Game.WIDTH * Game.SCALE / 2), 0, World.WIDTH*64 - Game.WIDTH * Game.SCALE);
        Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT * Game.SCALE / 2), 0, World.HEIGHT*64 - Game.HEIGHT * Game.SCALE);
    }

    public void render(Graphics g){
        if(direction == "left"){
            g.translate((this.getX() + 64) - Camera.x, this.getY() - Camera.y);
            ((Graphics2D) g).scale(-1, 1);
            g.translate(-this.getX() + Camera.x, -this.getY() + Camera.y);
        }
        g.drawImage(animation[curAnimation], (this.getX() - Camera.x)  - 64, (this.getY() - Camera.y) - 64, null);
        if(direction == "left"){
            g.translate((this.getX() + 64) - Camera.x, this.getY() - Camera.y);
            ((Graphics2D) g).scale(-1, 1);
            g.translate(-this.getX() + Camera.x, -this.getY() + Camera.y);
        }
    }
}
