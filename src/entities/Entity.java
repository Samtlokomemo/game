package entities;

import main.Game;
import world.Camera;
import world.WallTile;
import world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Math.round;

public class Entity{

    protected double x, y;
    protected int width, height;
    private final BufferedImage sprite;
    public BufferedImage[] animation;
    protected Rectangle mask = new Rectangle(this.getX(), this.getY() - Camera.y, World.TILE_SIZE, World.TILE_SIZE);

    public Entity(int x, int y, int width, int height, BufferedImage sprite){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
    }

    public int getX() {
        return (int)this.x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return (int)this.y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    public void tick(){

    }

    public boolean isFree(double x, double y){
        //Colisão com tiles
        for (WallTile currentBlock : World.blocks) {
            if(currentBlock.intersects(new Rectangle((int) x, (int) y, World.TILE_SIZE, World.TILE_SIZE))){
                return false;
            }
        }
        return true;
    }

    public boolean isColliding(int x, int y, Entity e){
        Rectangle entity = new Rectangle(x, y, mask.width, mask.height);
        Rectangle other = new Rectangle(e.getX(), e.getY(), mask.width, mask.height);
        return entity.intersects(other);
    }


    //Fórmula da distância euclidiana (Pitágoras) para acha a distância entre 2 pontos.
    public double pointDistance(int x1, int y1, int x2, int y2){
        return round(Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)));
    }

    public void render(Graphics g){
        g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
    }
}
