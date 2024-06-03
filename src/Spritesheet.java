import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Spritesheet {
    public static BufferedImage spritesheet;
    public static BufferedImage playerFront;

    public Spritesheet(){
        try{
            spritesheet = ImageIO.read(Objects.requireNonNull(getClass().getResource("playerSpritesheet.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
        playerFront = Spritesheet.getSprite(0, 0,25, 35);
    }

    public static BufferedImage getSprite(int x, int y, int width, int height){
        return spritesheet.getSubimage(x, y, width, height);
    }

}
