package main;

import java.awt.*;
import java.util.Objects;

public class Menu{
    private final String[] options = {"JOGAR", "SAIR"};
    private int currentOption = 0;
    private final int maxOptions = options.length - 1;
    public boolean up, down, enter, pause;

    public Menu(){

    }

    public void tick(){
        if(pause){
            options[0] = "CONTINUAR";
        }
        if(Game.win){
            options[0] = "SAIR";
            options[1] = "";
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
            if(Objects.equals(options[currentOption], "JOGAR") || Objects.equals(options[currentOption], "CONTINUAR")){
                Game.gameState = "GAME";
            }else if(Objects.equals(options[currentOption], "SAIR")){
                System.exit(0);
            }
        }

    }

    public static void desenharTextoCentralizado(Graphics g, String text, Font font, int y){
        g.setFont(font);

        FontMetrics metrics = g.getFontMetrics(font);
        int x = (Game.WIDTH * Game.SCALE - metrics.stringWidth(text)) / 2;

        g.drawString(text, x, y);
    }

    public static void desenharTextoCentralizado(Graphics g, String text, Font font, int x, int y){
        g.setFont(font);

        FontMetrics metrics = g.getFontMetrics(font);
        int adjustedX = x - metrics.stringWidth(text) / 2;
        g.drawString(text, adjustedX, y);
    }

    public void render(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(0, 0 ,0, 100));
        g2.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);

        if (pause){
            g.setColor(Color.white);
            desenharTextoCentralizado(g, "JOGO PAUSADO", new Font("arial", Font.BOLD, 48), 150);
        }else if(Game.win){
            g.setColor(Color.white);
            desenharTextoCentralizado(g, "VOCÊ VENCEU!", new Font("arial", Font.BOLD, 48), 150);
        }else{
            g.setColor(Color.white);
            desenharTextoCentralizado(g, "SEM NOME", new Font("arial", Font.BOLD, 32), 150);
        }

        //Opções

        for (int i = 0; i < options.length; i++) {
            String texto = options[i];
            if(Objects.equals(options[currentOption], options[i])){
                g.setColor(Color.yellow);
                texto = "> " + texto + " <";
            }else{
                g.setColor(Color.white);
            }
            desenharTextoCentralizado(g, texto, new Font("arial", Font.BOLD, 20), 220 + (35*i));
        }
    }
}
