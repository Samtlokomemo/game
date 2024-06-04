import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class World {

    public static int TILESIZE = 64;

    public static List<Block> blocks = new ArrayList<>(); //Blocos sólidos

    public World(){
        //Faz circular o mapa com paredes
        for (int xx = 0; xx < (Game.resolutionX / TILESIZE); xx++) {
            blocks.add(new Block(xx*TILESIZE, 0));
        }
        for (int xx = 0; xx < (Game.resolutionX / TILESIZE); xx++) {
            blocks.add(new Block(xx*TILESIZE, Game.resolutionY - TILESIZE));
        }

        for (int yy = 0; yy < (Game.resolutionY  / TILESIZE); yy++) {
            blocks.add(new Block(0, yy*TILESIZE));
        }
        for (int yy = 0; yy < (Game.resolutionY  / TILESIZE); yy++) {
            blocks.add(new Block(Game.resolutionX -TILESIZE, yy*TILESIZE));
        }
    }

    public static boolean isFree(int x, int y){
        for (Block currentBlock : blocks) {
            if (currentBlock.intersects(new Rectangle(x, y, TILESIZE, TILESIZE))) {
                return false;
            }
        }

        return true;
    }

    public void render(Graphics g){
        for (Block block : blocks) {
            block.render(g);
        }
    }
}
