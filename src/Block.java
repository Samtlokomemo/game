import java.awt.*;

public class Block extends Rectangle {
    public Block(int x, int y){
        super(x, y, 64, 64); //Extende da classe retângulo
    }

    public void render(Graphics g){
        g.drawImage(Spritesheet.chao, x, y, 64, 64, null);
        //g.drawImage(Spritesheet.chao, x, y, 64, 64, null);
    }
}
