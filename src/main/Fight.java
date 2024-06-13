package main;

import entities.Enemy;
import entities.Entity;
import entities.Player;
import graphics.Spritesheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

import static main.Game.gameState;

public class Fight {

    public String[] options = {"ATACAR", "ITEM"};
    public int currentOption = 0, maxOptions = options.length - 1, alpha;

    public boolean right, left, enter, playerTurn;

    //Sprites
    public int playerCurAnimation = 0, playerCurFrames = 0, playerTargetFrames = 7;
    public int enemyCurAnimation = 0, enemyCurFrames = 0, enemyTargetFrames = 7;
    public BufferedImage[] playerAnimation = Spritesheet.playerIdle;
    public BufferedImage[] enemyAnimation = Spritesheet.enemyIdle;

    public int pocoes = 5;

    public static int enemyLife = 100;
    public int playerLife = 100;

    String texto = "";

    public Fight(){
        alpha = 255;
        playerTurn = true;
    }

    public void tick() {
        if (alpha > 0) {
            alpha -= 5;
        }

        if (left) {
            left = false;
            currentOption--;
            if (currentOption < 0) {
                currentOption = maxOptions;
            }
        } else if (right) {
            right = false;
            currentOption++;
            if (currentOption > maxOptions) {
                currentOption = 0;
            }
        }

        if (enter && playerTurn) {

            enter = false;
            playerTurn = false;
            alpha = 255;
            switch (options[currentOption]) {
                case "ATACAR":
                    // Ataque
                    if (Math.random() < 0.7) {
                        playerCurAnimation = 0;
                        playerAnimation = Spritesheet.playerAtk;
                        enemyLife -= 10;
                        texto = "ACERTOU";
                    } else {
                        enemyCurAnimation = 0;
                        texto = "ERROU";
                        playerLife -= 10;
                        enemyAnimation = Spritesheet.enemyAtk;
                    }
                    break;
                case "ITEM":
                    // Item
                    if(playerLife < 100){
                        if(pocoes > 0){
                            pocoes -= 1;
                            texto = "CUROU 10 HP";
                            playerLife += 10;
                        }else{
                            texto = "VOCÊ NÃO TEM MAIS POÇÕES";
                        }
                    }else{
                        texto = "SUA VIDA ESTÁ CHEIA";
                    }
                    playerTurn = true;
                    break;
            }
        }

        if (enemyLife <= 0) {
            enter = false;
            gameState = "GAME";
            Enemy.lost = true;
            enemyLife = 100;
        }

        // Animação player
        if (playerCurFrames == playerTargetFrames) {
            playerCurFrames = 0;
            playerCurAnimation++;
            if (playerCurAnimation == playerAnimation.length) {
                playerCurAnimation = 0;
                if (playerAnimation == Spritesheet.playerAtk) {
                    playerAnimation = Spritesheet.playerIdle;
                    playerTurn = true;
                }
            }
        } else {
            playerCurFrames++;
        }

        // Animação inimigo
        if (enemyCurFrames == enemyTargetFrames) {
            enemyCurFrames = 0;
            enemyCurAnimation++;
            if (enemyCurAnimation == enemyAnimation.length) {
                enemyCurAnimation = 0;
                if (enemyAnimation == Spritesheet.enemyAtk) {
                    enemyAnimation = Spritesheet.enemyIdle;
                    playerTurn = true;
                }
            }
        } else {
            enemyCurFrames++;
        }
    }

    public void render(Graphics g){

        //Fundo
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(0, 0 ,0, 200));
        g2.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);

        g.setFont(new Font("arial", Font.BOLD, 36));
        int spacing = Game.WIDTH / (options.length + 1);

        //JANELA AZUL
        g2.setColor(new Color(0, 0 ,100, 100));
        g2.fillRect(spacing - (spacing / 2), 50, spacing * options.length, 400);

        //Barra de vida Jogador
        g2.setColor(new Color(112, 0 ,0, 255));
        g2.fillRect(spacing, 400, 192, 20);

        g2.setColor(new Color(0, 200 ,0, 255));
        g2.fillRect(spacing, 400, (int) ((playerLife/ (double) 100) * 192), 20);

        //Barra de vida Inimigo
        g2.setColor(new Color(112, 0 ,0, 255));
        g2.fillRect(spacing + 100, 200, 192, 20);

        g2.setColor(new Color(0, 200 ,0, 255));
        g2.fillRect(spacing + 100, 200, (int) ((enemyLife/ (double) 100) * 192), 20);

        //Menu e desenhos
        for (int i = 0; i < options.length; i++) {
            String texto = options[i];
            if(Objects.equals(options[currentOption], options[i])){
                g.setColor(Color.yellow);
                texto = "> " + texto + " <";
            }else{
                g.setColor(Color.white);
            }
            g.drawImage(enemyAnimation[enemyCurAnimation], spacing + 100, 50, null);
            g.drawImage(playerAnimation[playerCurAnimation], spacing, 250, null);
            Menu.desenharTextoCentralizado(g, texto, new Font("arial", Font.BOLD, 20), spacing * (i + 1),500);
        }

        //Texto
        g2.setColor(new Color(255, 255 ,255, alpha));
        Menu.desenharTextoCentralizado(g, texto, new Font("arial", Font.BOLD, 20),100);

    }
}
