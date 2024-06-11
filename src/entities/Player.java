package entities;

import graphics.Spritesheet;
import main.Game;
import org.w3c.dom.css.Rect;
import world.Camera;
import world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

import static graphics.Spritesheet.playerWalk;
import static graphics.Spritesheet.playerIdle;
import static graphics.Spritesheet.playerAtk;
import static main.Game.debugger;
import static main.Game.noKey;

public class Player extends Entity{
    public double spd = 6;
    public int timer = 0;
    public boolean right, up, down, left;
    public int curAnimation = 0, curFrames = 0, targetFrames = 7;
    public static BufferedImage[] animation = playerIdle;
    public boolean moved, attack = false;
    public String state = "idle";
    int direction;

    public Player(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);

        this.mask.x = this.getX() + 192;
        this.mask.y = this.getY() + 32;
        this.mask.width = 64;
        this.mask.height = 64;
    }

    public void tick(){
        //STATE MACHINE
        switch (state){
            case "idle":
                animation = playerIdle;
                if(moved){
                    curFrames = 0;
                    curAnimation = 0;
                    state = "walking";
                }
                if(attack){
                    curFrames = 0;
                    curAnimation = 0;
                    state = "attacking";
                }
                break;
            case "walking":
                animation = playerWalk;
                if(attack){
                    state = "attacking";
                }
                if(noKey){
                    state = "idle";
                }
                break;
        }

        //Movimentação
        if(right && isFree(x + spd, y)){
            x+=spd;
            direction = 1;
        } else if (left && isFree(x - spd, y)) {
            x-=spd;
            direction = -1;
        }

        if (up && isFree(x, y - spd)){
            y-=spd;
        } else if (down && isFree(x, y + spd)) {
            y+=spd;
        }

        //Animação
        if(curFrames == targetFrames){
            curFrames = 0;
            curAnimation++;
            if(curAnimation == animation.length){
                curAnimation = 0;
            }
        }else{
            curFrames++;
        }

        //Câmera
        Camera.x = Camera.clamp(this.getX() - (Game.WIDTH * Game.SCALE / 2), 0, World.WIDTH*64 - Game.WIDTH * Game.SCALE);
        Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT * Game.SCALE / 2), 0, World.HEIGHT*64 - Game.HEIGHT * Game.SCALE);
    }

    public void render(Graphics g){
        //Se quiser ver coisas de teste aperta -> ;
        if(debugger){
            System.out.println((this.getX() - Camera.x) + (28 * direction));
            g.setColor(Color.BLUE);
            g.fillRect(this.getX() - Camera.x, this.getY() - Camera.y, mask.width, mask.height);


        }

        if(direction == -1){
            g.translate((this.getX() + 64) - Camera.x, this.getY() - Camera.y);
            ((Graphics2D) g).scale(-1, 1);
            g.translate(-this.getX() + Camera.x, -this.getY() + Camera.y);
        }

        g.drawImage(animation[curAnimation], (this.getX() - Camera.x)  - 64, (this.getY() - Camera.y) - 64, null);

        if(direction == -1){
            g.translate((this.getX() + 64) - Camera.x, this.getY() - Camera.y);
            ((Graphics2D) g).scale(-1, 1);
            g.translate(-this.getX() + Camera.x, -this.getY() + Camera.y);
        }
    }
}
