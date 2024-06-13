package entities;

import graphics.Spritesheet;
import main.Fight;
import main.Game;
import world.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;

import static graphics.Spritesheet.enemyIdle;
import static graphics.Spritesheet.enemyWalk;
import static main.Game.debugger;
import static main.Game.gameState;

public class Enemy extends Entity{
    public int direction = 1;
    public static int cd = 60;
    public static boolean lost = false;

    public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        this.animation = enemyIdle;
        this.targetFrames = 6;
    }

    public void chasePlayer(){
        this.animation = enemyWalk;
        Player p = Game.player;
        double spd = 3;
        if(x < p.x && isFree(x + spd, y)){
            x+= spd;
            direction = 1;
        }else if(x > p.x && isFree(x - spd, y)){
            x-= spd;
            direction = -1;
        }
        if(y > p.y && isFree(x, y - spd)){
            y-= spd;
        }else if(y < p.y && isFree(x,y + spd)){
            y+= spd;
        }
    }

    public void tick(){
        String state = "idle";

        //Se o jogador estiver em uma área de 200 pixels o inimigo segue ele
        if(pointDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 300){
            state = "chase";
        }else{
            cd = 60;
        }

        switch (state){
            case "idle":
                this.animation = enemyIdle;
                break;
            case "chase":
                cd--;
                if(cd <= 0){
                    chasePlayer();
                }
                break;
        }

        //COMBATE
        if(this.collisionCheck(this, Game.player, x , y)){
            gameState = "FIGHT";
        }

        if(lost){
            lost = false;
            this.destroy = true;
            gameState = "GAME";
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
        if(debugger){
            g.setColor(Color.BLUE);
            g.fillRect(this.getX() - Camera.x, this.getY() - Camera.y, mask.width, mask.height);

            g.setColor(Color.red);
            g.drawLine(this.getX() - Camera.x, this.getY() - Camera.y, Game.player.getX() - Camera.x, Game.player.getY() - Camera.y);
            g.drawString("Distância: " + pointDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()), this.getX() - Camera.x, this.getY() - 10 - Camera.y);
        }

        if(direction == -1){
            g.translate((this.getX() + 64) - Camera.x, this.getY() - Camera.y);
            ((Graphics2D) g).scale(-1, 1);
            g.translate(-this.getX() + Camera.x, -this.getY() + Camera.y);
        }

        g.drawImage(this.animation[this.curAnimation], (this.getX() - Camera.x) - 64, (this.getY() - Camera.y) - 64, null);

        if(direction == -1){
            g.translate((this.getX() + 64) - Camera.x, this.getY() - Camera.y);
            ((Graphics2D) g).scale(-1, 1);
            g.translate(-this.getX() + Camera.x, -this.getY() + Camera.y);
        }
    }
}

