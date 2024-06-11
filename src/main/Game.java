package main;

import entities.Enemy;
import entities.Entity;
import entities.Player;
import graphics.Spritesheet;
import world.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

public class Game extends Canvas implements Runnable, KeyListener {
    public static boolean debugger = false;
    public static int WIDTH = 960, HEIGHT = 576; //Tamanho da janela
    public static int SCALE = 1;

    public static ArrayList<Entity> entities;
    public static ArrayList<Entity> enemies;

    public static Player player;
    public World world;
    public Menu menu;
    public static boolean noKey;
    public static String gameState = "MENU";
    public static BufferedImage image;

    public Game(){
        this.addKeyListener(this);
        setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
        //this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        new Spritesheet();

        entities = new ArrayList<>();
        enemies = new ArrayList<>();

        player = new Player(0, 0, 192, 192, Spritesheet.getSprite(Spritesheet.playerSpritesheet, 0, 0, 192, 192));
        entities.add(player);

        world = new World("/map.png"); //Cria o cenário

        menu = new Menu();

    }

    public void tick(){ //Onde fica toda a lógica
        if(Objects.equals(gameState, "GAME")){
            for (Entity e : entities) {
                e.tick();
            }
            for (Entity e : enemies){
                e.tick();
            }
        }else if(Objects.equals(gameState, "MENU")){
            menu.tick();
        }
    }

    public void render(){ //Onde renderiza as imagens
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){ //Se não existir cria um
            this.createBufferStrategy(3);
            return;
        }



        //Fundo da tela
        Graphics g = image.getGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);

        //Renderização dos objetos.
        world.render(g);
        //player.render(g);
        for (Entity e : entities) {
            e.render(g);
        }
        for (Entity e : enemies) {
            e.render(g);
        }

        if(Objects.equals(gameState, "MENU")){
            menu.render(g);
        }

        g.dispose();
        g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height, null);

        bs.show();
    }

    public static void main(String[] args) {
        //Criação da janela
        Game game = new Game();
        JFrame frame = new JFrame("JOGO");

        frame.add(game);
        frame.setUndecorated(true);
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

    //TODO GAME STATES COM SISTEMA DE ATAQUE!

    //MAPEAMENTO TECLADO

    @Override
    public void keyTyped(KeyEvent e) {


    }

    @Override
    public void keyPressed(KeyEvent e) {
        noKey = false;

        if(e.getKeyCode() == KeyEvent.VK_W ||
           e.getKeyCode() == KeyEvent.VK_UP){
            player.up = true;
            player.moved = true;
            if(Objects.equals(gameState, "MENU")){
                menu.up = true;
            }
        }else if(e.getKeyCode() == KeyEvent.VK_A ||
                 e.getKeyCode() == KeyEvent.VK_LEFT){
            player.left = true;
            player.moved = true;
        }else if(e.getKeyCode() == KeyEvent.VK_S ||
                 e.getKeyCode() == KeyEvent.VK_DOWN){
            player.down = true;
            player.moved = true;
            if(Objects.equals(gameState, "MENU")){
                menu.down = true;
            }
        }else if(e.getKeyCode() == KeyEvent.VK_D ||
                 e.getKeyCode() == KeyEvent.VK_LEFT){
            player.right = true;
            player.moved = true;
        }

        if(e.getKeyCode() == KeyEvent.VK_SEMICOLON){
            debugger = !debugger;
        }

        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(Objects.equals(gameState, "MENU")){
                menu.enter = true;
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            if(Objects.equals(gameState, "GAME")){
                gameState = "MENU";
                menu.pause = true;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        noKey = true;
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
