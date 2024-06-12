package main;

import entities.Enemy;
import graphics.Spritesheet;

import java.awt.*;
import java.util.Objects;

import static main.Game.gameState;

public class Fight {

    public String[] options = {"ATACAR", "DEFENDER", "ITEM", "FUGIR"};
    public int currentOption = 0, maxOptions = options.length - 1, alpha;

    public boolean right, left, enter;
    public static boolean atacar, defender, item, fugir, falha;

    public Fight(){
        alpha = 255;
    }

    public void tick(){
        if (alpha > 0) {
            alpha -= 5;
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

        if(enter){
            System.out.println(options[currentOption]);
            enter = false;
            alpha = 255;
            atacar = false;
            defender = false;
            item = false;
            fugir = false;

            switch (options[currentOption]) {
                case "ATACAR":
                    // Ataque
                    if (Math.random() < 0.5) {
                        atacar = true;
                    } else {
                        falha = true;
                    }
                    break;
                case "DEFENDER":
                    // Defesa
                    if (Math.random() < 0.5) {
                        defender = true;
                        falha = false;
                    } else {
                        falha = true;
                    }
                    break;
                case "ITEM":
                    // Item
                    item = true;
                    falha = false;
                    break;
                case "FUGIR":
                    // Sair
                    fugir = true;
                    falha = false;
                    break;
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
            g.drawImage(Spritesheet.enemyIdle[0], spacing, 50, null);
            Menu.desenharTextoCentralizado(g, texto, new Font("arial", Font.BOLD, 20), spacing * (i + 1),500);
        }
        if(fugir){
            g2.setColor(new Color(255, 0 ,0, alpha));
            Menu.desenharTextoCentralizado(g, "VOCÊ NÃO PODE FUGIR", new Font("arial", Font.BOLD, 20),100);
        }
        if(atacar){
            g2.setColor(new Color(255, 255 ,0, alpha));
            Menu.desenharTextoCentralizado(g, "ATACOU", new Font("arial", Font.BOLD, 20),100);
        }
        if(item){
            g2.setColor(new Color(255, 255 ,100, alpha));
            Menu.desenharTextoCentralizado(g, "VOCÊ USOU O ITEM", new Font("arial", Font.BOLD, 20),100);
        }
        if(defender){
            g2.setColor(new Color(0, 0 ,255, alpha));
            Menu.desenharTextoCentralizado(g, "DEFENDEU", new Font("arial", Font.BOLD, 20),100);
        }
    }
}
