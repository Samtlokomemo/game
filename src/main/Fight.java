package main;

import java.awt.*;
import java.util.Objects;

import static main.Game.gameState;

public class Fight {

    public static boolean fighting = false, pressed = false;

    public Fight(){

    }

    public void tick(){
        if(Objects.equals(gameState, "FIGHT")){
            fighting = true;
            if(pressed){
                gameState = "GAME";
                fighting = false;
                pressed = false;
            }
        }
    }

    public void render(Graphics g){
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
    }
}
