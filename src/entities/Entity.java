package entities;

import main.Game;
import world.Camera;
import world.WallTile;
import world.World;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static java.lang.Math.round;

public class Entity{
    //Métodos da construção da entidade
    protected double x, y;
    protected int width, height;
    private final BufferedImage sprite;


    public boolean destroy = false;

    //Animação
    protected BufferedImage[] animation;
    protected int curAnimation = 0, curFrames = 0, targetFrames;

    //Máscara de colisão
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

    protected boolean isFree(double x, double y){
        //Colisão com tiles
        for (WallTile currentBlock : World.blocks) {
            if(currentBlock.intersects(new Rectangle((int) x, (int) y, World.TILE_SIZE, World.TILE_SIZE))){
                return false;
            }
        }
        return true;
    }

    protected boolean collisionCheck(Entity e1, Entity e2, double x, double y){
        Rectangle bbox1 = getMask(e1, x, y);
        Rectangle bbox2 = getMask(e2, e2.x, e2.y);
        return bbox1.intersects(bbox2);
    }

    private Rectangle getMask(Entity e, double x, double y) {
        int width = e.mask.width;
        int height = e.mask.height;
        return new Rectangle((int) x, (int) y, width, height);
    }


    //Fórmula da distância euclidiana (Pitágoras) para acha a distância entre 2 pontos.
    protected double pointDistance(int x1, int y1, int x2, int y2){
        return round(Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)));
    }

    public void render(Graphics g){
        g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
    }
}
