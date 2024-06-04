import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Spritesheet {
    public static BufferedImage playerSpritesheet, tileSpritesheet;
    public static BufferedImage[] playerIdle, playerWalk;
    public static BufferedImage chao;

    public Spritesheet(){
        try{
            //Lugar onde carregamos nossas spirtesheets
            playerSpritesheet = ImageIO.read(Objects.requireNonNull(getClass().getResource("playerSpritesheet.png")));
            tileSpritesheet = ImageIO.read(Objects.requireNonNull(getClass().getResource("tileSpritesheet.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
        //Lugar onde colocamos em que parte da spritesheet o sprite em específico está
        playerIdle = new BufferedImage[6];
        playerIdle[0] = Spritesheet.getSprite(playerSpritesheet, 0, 0, 192, 192);
        playerIdle[1] = Spritesheet.getSprite(playerSpritesheet, 192, 0, 192, 192);
        playerIdle[2] = Spritesheet.getSprite(playerSpritesheet, 384, 0, 192, 192);
        playerIdle[3] = Spritesheet.getSprite(playerSpritesheet, 576, 0, 192, 192);
        playerIdle[4] = Spritesheet.getSprite(playerSpritesheet, 768, 0, 192, 192);
        playerIdle[5] = Spritesheet.getSprite(playerSpritesheet, 960, 0, 192, 192);

        playerWalk = new BufferedImage[6];
        playerWalk[0] = Spritesheet.getSprite(playerSpritesheet, 0, 192, 192, 192);
        playerWalk[1] = Spritesheet.getSprite(playerSpritesheet, 192, 192, 192, 192);
        playerWalk[2] = Spritesheet.getSprite(playerSpritesheet, 384, 192, 192, 192);
        playerWalk[3] = Spritesheet.getSprite(playerSpritesheet, 576, 192, 192, 192);
        playerWalk[4] = Spritesheet.getSprite(playerSpritesheet, 768, 192, 192, 192);
        playerWalk[5] = Spritesheet.getSprite(playerSpritesheet, 960, 192, 192, 192);

        chao = Spritesheet.getSprite(tileSpritesheet,256, 64,64, 64);
        //grama = Spritesheet.getSprite(tileSpritesheet,1152, 64,64, 64);
    }

    public static BufferedImage getSprite(BufferedImage spritesheet, int x, int y, int width, int height){
        return spritesheet.getSubimage(x, y, width, height);
    }

}
