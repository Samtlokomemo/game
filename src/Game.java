import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable, KeyListener {
    public static int WIDHT = 320, HEIGHT = 180; //Tamanho da janela
    public static int SCALE = 4;

    public static int resolutionX = WIDHT * SCALE;
    public static int resolutionY = HEIGHT * SCALE;
    public Player player;
    public World world;

    public Game(){
        this.addKeyListener(this);
        this.setPreferredSize(new Dimension(resolutionX, resolutionY));
        new Spritesheet();
        player = new Player(resolutionX / 2, resolutionY / 2); //Cria o jogador nessas posições da tela
        world = new World(); //Cria o cenário
    }

    public void tick(){ //Onde fica toda a lógica
        player.tick();
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
        g.fillRect(0, 0, resolutionX, resolutionY);

        //Renderização dos objetos.
        player.render(g);
        world.render(g);

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
        if(e.getKeyCode() == KeyEvent.VK_D){
            player.right = true;
        }else if(e.getKeyCode() == KeyEvent.VK_A){
            player.left = true;
        }

        if(e.getKeyCode() == KeyEvent.VK_W){
            player.up = true;
        }else if(e.getKeyCode() == KeyEvent.VK_S){
            player.down = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_D){
            player.right = false;
        }else if(e.getKeyCode() == KeyEvent.VK_A){
            player.left = false;
        }

        if(e.getKeyCode() == KeyEvent.VK_W){
            player.up = false;
        }else if(e.getKeyCode() == KeyEvent.VK_S){
            player.down = false;
        }
    }
}
