package main;

import java.awt.*;
import java.util.Objects;

import static main.Game.gameState;

public class Fight {

    public String[] options = {"ATACAR", "DEFENDER", "ITEM", "FUGIR"};
    public int currentOption = 0, maxOptions = options.length - 1;

    public boolean right, left, enter;
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

        if(left){
            left = false;
            currentOption--;
            if(currentOption < 0){
                currentOption = maxOptions;
            }
        }else if(right){
            right = false;
            currentOption++;
            if(currentOption > maxOptions){
                currentOption = 0;
            }
        }
    }

    public void render(Graphics g){

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(0, 0 ,0, 200));
        g2.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);


        g.setFont(new Font("arial", Font.BOLD, 36));

        int spacing = Game.WIDTH / (options.length + 1);

        g2.setColor(new Color(0, 0 ,0, 255));
        g2.fillRect(spacing - (spacing / 2), 50, spacing * options.length, 400);

        for (int i = 0; i < options.length; i++) {
            String texto = options[i];
            if(Objects.equals(options[currentOption], options[i])){
                g.setColor(Color.yellow);
                texto = "> " + texto + " <";
            }else{
                g.setColor(Color.white);
            }
            //g.drawString(texto, 50 + (200 * i), 300);
            Menu.desenharTextoCentralizado(g, texto, new Font("arial", Font.BOLD, 20), spacing * (i + 1),500);
        }
    }
}
