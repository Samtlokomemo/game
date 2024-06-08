package main;

import entities.Entity;
import entities.Player;
import graphics.Spritesheet;
import world.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Game extends Canvas implements Runnable, KeyListener {
    public static int WIDTH = 192, HEIGHT = 128; //Tamanho da janela
    public static int SCALE = 4;

    private BufferedImage image;

    public static ArrayList<Entity> entities;

    public static Player player;
    public World world;

    public Game(){
        this.addKeyListener(this);
        this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

        new Spritesheet();
        entities = new ArrayList<>();

        player = new Player(0, 0, 192, 192, Spritesheet.getSprite(Spritesheet.playerSpritesheet, 0, 0, 192, 192));
        entities.add(player);

        world = new World("/map.png"); //Cria o cenário

    }

    public void tick(){ //Onde fica toda a lógica
        for (Entity e : entities) {
            e.tick();
        }
    }

    public void render(){ //Onde renderiza as imagens
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){ //Se não existir cria um
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        //Fundo da tela
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);

        //Renderização dos objetos.
        world.render(g);
        //player.render(g);
        for (Entity e : entities) {
            e.render(g);
        }

        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        //Criação da janela
        Game game = new Game();
        JFrame frame = new JFrame();

        frame.add(game);
        frame.setTitle("Jogo");

        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);


        //Chama a função run
        new Thread(game).start();
    }

    @Override
    public void run() {
        while(true){
            tick();
            render();
            try {
                Thread.sleep(1000/60); //Seta para 60FPS
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W){
            player.up = true;
            player.moved = true;
        }else if(e.getKeyCode() == KeyEvent.VK_A){
            player.left = true;
            player.moved = true;
        }else if(e.getKeyCode() == KeyEvent.VK_S){
            player.down = true;
            player.moved = true;
        }else if(e.getKeyCode() == KeyEvent.VK_D){
            player.right = true;
            player.moved = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W){
            player.up = false;
            player.moved = false;
        }else if(e.getKeyCode() == KeyEvent.VK_A){
            player.left = false;
            player.moved = false;
        }else if(e.getKeyCode() == KeyEvent.VK_S){
            player.down = false;
            player.moved = false;
        }else if(e.getKeyCode() == KeyEvent.VK_D){
            player.right = false;
            player.moved = false;
        }
    }
}