package main;

import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    //Screen settings

    final int originalTileSize = 16; //16x16 tile size
    final int scale = 3; //scale the tile size by 3

    public final int tileSize = originalTileSize * scale; //48x48 actual tile size
    public final int maxScreenCol = 16; //16 columns on the screen
    public final int maxScreenRow = 12; //12 rows on the screen
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    //576x768 actual screen size

    // World settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    //FPS
    int FPS = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread; // thread for the game loop. this will call the run method
    public CollisionChecker cChecker = new CollisionChecker(this);
    public Player player = new Player(this, keyH); // create a player object

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // improve game's rendering performance
        this.addKeyListener(keyH); // add key listener to the game panel
        this.setFocusable(true); // make the game panel focusable so it can receive key events
    }

    public void startGameThread() {
        gameThread = new Thread(this); //create a new thread for the game
        gameThread.start(); //start the thread, which will call the run method
    }

    @Override
    public void run() {

        double drawInterval = (double) 1000000000 /FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1) {
            // 1. UPDATE: character positions
            update();
            // 2. DRAW: characters, tiles, etc. to the screen with updated information
            repaint();
            delta--;
            drawCount++;
            }

            if(timer >= 1000000000){
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }



        }
    }

    public void update() {

        player.update();
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        tileM.draw(g2);

        player.draw(g2);

        g2.dispose();
    }
}
