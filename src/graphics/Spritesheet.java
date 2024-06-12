package graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Spritesheet {
    public static BufferedImage playerSpritesheet, tileSpritesheet, enemySpritesheet, bg, water, treeSpritesheet;
    public static BufferedImage[] playerIdle, playerWalk, playerAtk, enemyIdle, enemyWalk, enemyAtk, treeIdle;

    public Spritesheet(){
        try{
            //Lugar onde carregamos nossas spirtesheets
            playerSpritesheet = ImageIO.read(Objects.requireNonNull(getClass().getResource("/playerSpritesheet.png")));
            tileSpritesheet = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tileSpritesheet.png")));
            enemySpritesheet = ImageIO.read(Objects.requireNonNull(getClass().getResource("/enemySpritesheet.png")));
            water = ImageIO.read(Objects.requireNonNull(getClass().getResource("/water.png")));
            treeSpritesheet = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tree.png")));
            bg = ImageIO.read(Objects.requireNonNull(getClass().getResource("/bg.jpeg")));

        }catch (IOException e){
            e.printStackTrace();
        }
        //Lugar onde colocamos em que parte da spritesheet o sprite em específico está

        //Sprites player
        playerIdle = new BufferedImage[6];
        for (int i = 0; i < 6; i++) {
            playerIdle[i] = Spritesheet.getSprite(Spritesheet.playerSpritesheet, i * 192, 0, 192, 192);
        }

        playerWalk = new BufferedImage[6];
        for (int i = 0; i < 6; i++) {
            playerWalk[i] = Spritesheet.getSprite(Spritesheet.playerSpritesheet, i * 192, 192, 192, 192);
        }

        playerAtk = new BufferedImage[6];
        for (int i = 0; i < 6; i++) {
            playerAtk[i] = Spritesheet.getSprite(Spritesheet.playerSpritesheet, i * 192, 384, 192, 192);
        }

        //Sprites inimigo
        enemyIdle = new BufferedImage[6];
        for (int i = 0; i < 6; i++) {
            enemyIdle[i] = Spritesheet.getSprite(Spritesheet.enemySpritesheet, i * 192, 0, 192, 192);
        }

        enemyWalk = new BufferedImage[6];
        for (int i = 0; i < 6; i++) {
            enemyWalk[i] = Spritesheet.getSprite(Spritesheet.enemySpritesheet, i * 192, 192, 192, 192);
        }

        enemyAtk = new BufferedImage[6];
        for (int i = 0; i < 6; i++) {
            enemyAtk[i] = Spritesheet.getSprite(Spritesheet.enemySpritesheet, i * 192, 384, 192, 192);
        }

        //Árvore
        treeIdle = new BufferedImage[5];
        for (int i = 0; i < 5; i++) {
            treeIdle[i] = Spritesheet.getSprite(Spritesheet.treeSpritesheet, i * 112, 0, 112, 174);
        }
    }

    public static BufferedImage getSprite(BufferedImage spritesheet, int x, int y, int width, int height){
        return spritesheet.getSubimage(x, y, width, height);
    }

}
