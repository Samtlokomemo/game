import java.awt.*;

public class Player extends Rectangle{

    public int spd = 4;
    public boolean right, up, down, left;

    public Player(int x, int y){
        super(x, y, 50, 70);
    }

    public void tick(){
        if(right && World.isFree(x + spd, y)){
            x+=spd;
        } else if (left && World.isFree(x - spd, y)) {
            x-=spd;
        }

        if (up && World.isFree(x, y - spd)){
            y-=spd;
        } else if (down && World.isFree(x, y + spd)) {
            y+=spd;
        }
    }

    public void render(Graphics g){
        //g.setColor(Color.BLUE);
        //g.fillRect(x, y, width, height);

        g.drawImage(Spritesheet.playerFront, x, y, 50, 70, null);
    }
}
