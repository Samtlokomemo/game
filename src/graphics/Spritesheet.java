package graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Spritesheet {
    public static BufferedImage playerSpritesheet, tileSpritesheet, enemySpritesheet;

    public Spritesheet(){
        try{
            //Lugar onde carregamos nossas spirtesheets
            playerSpritesheet = ImageIO.read(Objects.requireNonNull(getClass().getResource("/playerSpritesheet.png")));
            tileSpritesheet = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tileSpritesheet.png")));
            enemySpritesheet = ImageIO.read(Objects.requireNonNull(getClass().getResource("/enemySpritesheet.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
        //Lugar onde colocamos em que parte da spritesheet o sprite em específico está
    }

    public static BufferedImage getSprite(BufferedImage spritesheet, int x, int y, int width, int height){
        return spritesheet.getSubimage(x, y, width, height);
    }

}
