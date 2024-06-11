package entities;

import main.Game;

import java.awt.image.BufferedImage;

public class Attack extends Entity{
    public Attack(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
    }

    public boolean Attacked(){
        for (int i = 0; i < Game.entities.size(); i++) {
            Entity e = Game.enemies.get(i);
            if(isColliding(this.getX(), this.getY(), e)){
                return true;
            }
        }
        return false;
    }

}
