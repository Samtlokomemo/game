package world;

import entities.Enemy;
import graphics.Spritesheet;
import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class World {

    private Tile[] tiles;
    public static ArrayList<WallTile> blocks = new ArrayList<>();
    public static int TILE_SIZE = 64;
    public static int WIDTH, HEIGHT;


    public World(String path){
        try{
            BufferedImage map = ImageIO.read(Objects.requireNonNull(getClass().getResource(path)));
            int[] pixels = new int[map.getWidth() * map.getHeight()];
            WIDTH = map.getWidth();
            HEIGHT = map.getHeight();
            tiles = new Tile[map.getWidth() * map.getHeight()];
            map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());

            //Roda pixel a pixel do nosso mapa e checa que cor é o pixel.
            for (int xx = 0; xx < map.getWidth(); xx++) {
                for (int yy = 0; yy < map.getHeight(); yy++) {
                    tiles[xx + (yy * WIDTH)] = new FloorTile(xx*64, yy*64,Tile.TILE_FLOOR);
                    int pixelAtual = pixels[xx + (yy * map.getWidth())];
                    if(pixelAtual == 0xFFFFFFFF) {
                        //Parede
                        blocks.add(new WallTile(xx * TILE_SIZE, yy * TILE_SIZE, Tile.TILE_WALL));
                        for (WallTile block : blocks) {
                            tiles[xx + (yy * WIDTH)] = block;
                        }
                    } else if (pixelAtual == 0xFF00ffff) {
                        //Água
                        blocks.add(new WallTile(xx * TILE_SIZE, yy * TILE_SIZE, Tile.WATER));
                        for (WallTile block : blocks) {
                            tiles[xx + (yy * WIDTH)] = block;
                        }
                    }else if (pixelAtual == 0xFFff66ff) {
                        //Carne
                        //Game.entities.add(new Itens(xx * TILE_SIZE, yy * TILE_SIZE, 192, 192, Spritesheet.meat));
                    }else if(pixelAtual == 0xFF124e89) {
                        //Player
                        Game.player.setX(xx * TILE_SIZE);
                        Game.player.setY(yy * TILE_SIZE);
                    }else if(pixelAtual == 0xFFff0044) {
                        //Inimigo
                        Game.enemies.add(new Enemy(xx * TILE_SIZE, yy * TILE_SIZE, 192, 192, Spritesheet.enemySpritesheet));
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void render(Graphics g){
        int xStart = Camera.x / 64;
        int yStart = Camera.y / 64;
        int xFinal = xStart + ((Game.WIDTH) / 64);
        int yFinal = yStart + ((Game.HEIGHT) / 64);

        for (int xx = xStart; xx <= xFinal; xx++) {
            for (int yy = yStart; yy <= yFinal; yy++) {
                if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
                    continue;
                Tile tile = tiles[xx + (yy * WIDTH)];
                tile.render(g);
            }

        }



    }
}
