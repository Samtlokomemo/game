import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Rectangle{

    public int spd = 6;
    public boolean right, up, down, left;

    public int curAnimation = 0, curFrames = 0, targetFrames = 6;
    public static BufferedImage[] animation = Spritesheet.playerIdle;
    boolean moved = false;
    String direction;

    public Player(int x, int y){
        super(x, y, 64, 64);
    }

    public void tick(){
        if(right && World.isFree(x + spd, y)){
            x+=spd;
            direction = "right";
        } else if (left && World.isFree(x - spd, y)) {
            x-=spd;
            direction = "left";
        }

        if (up && World.isFree(x, y - spd)){
            y-=spd;
        } else if (down && World.isFree(x, y + spd)) {
            y+=spd;
        }

        if(moved){
            animation = Spritesheet.playerWalk;
        }else{
            animation = Spritesheet.playerIdle;
        }

        //Animação
        curFrames++;
        if(curFrames == targetFrames){
            curFrames = 0;
            curAnimation++;
            if(curAnimation == animation.length){
                curAnimation = 0;
            }
        }
    }

    public void render(Graphics g){
        //g.setColor(Color.BLUE);
        //g.fillRect(x, y, width, height);

        if(direction == "left"){
            g.translate(x + 64, y);
            ((Graphics2D) g).scale(-1, 1);
            g.translate(-x, -y);
        }

        g.drawImage(animation[curAnimation], x - 64, y - 42, 192, 192, null);

        if(direction == "left"){
            g.translate(x + 64, y);
            ((Graphics2D) g).scale(-1, 1);
            g.translate(-x, -y);
        }
    }
}
