package entities;

import main.Game;
import world.Camera;
import world.WallTile;
import world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity{

    protected double x, y;
    protected int width, height;
    private BufferedImage sprite;
    protected Rectangle mask = new Rectangle(this.getX(), this.getY(), World.TILE_SIZE, World.TILE_SIZE);

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

    public boolean isFree(int x, int y){
        //Colisão com tiles
        for (WallTile currentBlock : World.blocks) {
            if(currentBlock.intersects(new Rectangle(x, y, World.TILE_SIZE, World.TILE_SIZE))){
                return false;
            }
        }
        return true;
    }

    public void render(Graphics g){
        g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
    }
}
