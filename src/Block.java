import java.awt.*;

public class Block extends Rectangle {
    public Block(int x, int y){
        super(x, y, 32, 32); //Extende da classe retângulo
    }

    public void render(Graphics g){
        g.setColor(Color.MAGENTA);
        g.fillRect(x, y, width, height);
        g.setColor(Color.WHITE);
        g.drawRect(x, y, width, height);
    }
}
