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
import java.util.Iterator;
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
    public Fight fight;
    public static boolean noKey;
    public static String gameState = "MENU";
    public static BufferedImage image;

    public static JFrame frame;

    public Game(){
        this.addKeyListener(this);
        setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        new Spritesheet();

        entities = new ArrayList<>();
        enemies = new ArrayList<>();

        player = new Player(0, 0, 192, 192, Spritesheet.getSprite(Spritesheet.playerSpritesheet, 0, 0, 192, 192));
        entities.add(player);

        world = new World("/level1.png"); //Cria o cenário

        fight = new Fight();
        menu = new Menu();

    }

    public void tick(){ //Onde fica toda a lógica
        if(Objects.equals(gameState, "GAME")){
            for (Entity e : entities) {
                e.tick();
            }
        Iterator<Entity> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            Entity enemy = iterator.next();
            enemy.tick(); // Chama o método tick do inimigo

            // Se o inimigo foi derrotado
            if (enemy.defeat) {
                iterator.remove(); // Remove o inimigo da lista de forma segura
            }
        }

        }else if(Objects.equals(gameState, "MENU")){
            menu.tick();
        } else if (Objects.equals(gameState, "FIGHT")) {
            fight.tick();
        }
    }

    public void render(){ //Onde renderiza as imagens
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        //Pega a imagem no tamanho original
        BufferedImage originalImage = new BufferedImage(WIDTH * SCALE, HEIGHT * SCALE, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = originalImage.createGraphics();

        // Fundo da tela
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);

        // Renderização dos objetos
        world.render(g2d);
        for (Entity e : entities) {
            e.render(g2d);
        }
        if (!enemies.isEmpty()) {
            for (Entity e : enemies) {
                e.render(g2d);
            }
        }
        if (Objects.equals(gameState, "MENU")) {
            menu.render(g2d);
        } else if (Objects.equals(gameState, "FIGHT")) {
            fight.render(g2d);
        }

        g2d.dispose();

        //Pega o tamanho da janela
        int currentWidth = getWidth();
        int currentHeight = getHeight();

        //Ajusta no tamanho da janela
        Graphics g = bs.getDrawGraphics();
        g.drawImage(originalImage, 0, 0, currentWidth, currentHeight, null);

        g.dispose();
        bs.show();
    }

    public static JFrame getWindow() {
        return frame;
    }

    public static void main(String[] args) {
        //Criação da janela
        Game game = new Game();

        frame = new JFrame("JOGO");
        frame.add(game);
        frame.setResizable(true);
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
        requestFocus();
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
            if(Objects.equals(gameState, "FIGHT")){
                fight.left = true;
            }
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
            if(Objects.equals(gameState, "FIGHT")){
                fight.right = true;
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_SEMICOLON){
            debugger = !debugger;
        }

        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(Objects.equals(gameState, "MENU")){
                menu.enter = true;
            }else if(Objects.equals(gameState, "FIGHT")){
                fight.enter = true;
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
