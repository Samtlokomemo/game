package entities;

import graphics.Spritesheet;
import main.Game;
import world.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;

import static main.Game.debugger;

public class Enemy extends Entity{

    public static BufferedImage slimeSprite = Spritesheet.getSprite(Spritesheet.enemySpritesheet, 0, 0, 192, 192);
    public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
    }

    public void chasePlayer(){
        //TODO MELHORAR O MOVIMENTO
        Player p = Game.player;
        double spd = 3;
        if(x < p.x && isFree(x + spd, y)){
            x+= spd;
        }else if(x > p.x && isFree(x - spd, y)){
            x-= spd;
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
        }


        switch (state){
            case "idle":
                //TODO Fazer andar de um lado para o outro
                break;
            case "chase":
                chasePlayer();
                break;
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

        g.drawImage(slimeSprite, (this.getX() - Camera.x) - 64, (this.getY() - Camera.y) - 64, null);
    }
}
