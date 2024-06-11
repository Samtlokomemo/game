package main;

import java.awt.*;
import java.io.*;
import java.util.Objects;

import static graphics.Spritesheet.bg;

public class Menu{
    public String[] options = {"NOVO JOGO", "OPÇÕES", "SAIR"};
    public String[] selectedOptions = {"> NOVO JOGO <", "> OPÇÕES <", "> SAIR <"};
    public int currentOption = 0, maxOptions = options.length - 1;
    public boolean up, down, enter, pause;

    public Menu(){

    }

    public void tick(){
        if(pause){
            options[0] = "CONTINUAR";
            selectedOptions[0] = "> CONTINUAR <";
        }
        if(up){
            up = false;
            currentOption--;
            if(currentOption < 0){
                currentOption = maxOptions;
            }
        }else if(down){
            down = false;
            currentOption++;
            if(currentOption > maxOptions){
                currentOption = 0;
            }
        }

        if(enter){
            enter = false;
            if(Objects.equals(options[currentOption], "NOVO JOGO") || Objects.equals(options[currentOption], "CONTINUAR")){
                Game.gameState = "GAME";
            }else if(Objects.equals(options[currentOption], "SAIR")){
                System.exit(0);
            }
        }

    }

    public void desenharTextoCentralizado(Graphics g, String text, Font font, int y){
        g.setFont(font);

        FontMetrics metrics = g.getFontMetrics(font);
        int x = (Game.WIDTH * Game.SCALE - metrics.stringWidth(text)) / 2;
        //int y = ((Game.HEIGHT * Game.SCALE - metrics.getHeight()) / 2) + metrics.getAscent();

        g.drawString(text, x, y);
    }

    public void render(Graphics g){
        if(!pause){
            g.drawImage(bg, 0, 0, null);
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(0, 0 ,0, 100));
        g2.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);

        if (!pause){
            g.setColor(Color.white);
            desenharTextoCentralizado(g, "Título do jogo", new Font("arial", Font.BOLD, 32), 150);
        }else{
            g.setColor(Color.white);
            desenharTextoCentralizado(g, "JOGO PAUSADO", new Font("arial", Font.BOLD, 32), 150);
        }

        //Opções

        for (int i = 0; i < options.length; i++) {
            String texto = options[i];
            if(Objects.equals(options[currentOption], options[i])){
                g.setColor(Color.yellow);
                texto = selectedOptions[i];
            }else{
                g.setColor(Color.white);
            }
            desenharTextoCentralizado(g, texto, new Font("arial", Font.BOLD, 24), 220 + (35*i));
        }
    }
}
