package main;

import entity.Entity;
import entity.Player;
import object.SuperObject;
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
//    public final int worldWidth = tileSize * maxWorldCol;
//    public final int worldHeight = tileSize * maxWorldRow;

    //FPS
    public int FPS = 60;

    //SYSTEM
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();

    //COLLISION AND ASSETS
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread; // thread for the game loop. this will call the run method

    //ENTITY AND OBJECT
    public Player player = new Player(this, keyH); // create a player object
    public SuperObject[] obj = new SuperObject[10]; // array to hold objects in the game
    public Entity[] npc = new Entity[10];

    //GAME STATE
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // improve game's rendering performance
        this.addKeyListener(keyH); // add key listener to the game panel
        this.setFocusable(true); // make the game panel focusable so it can receive key events
    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
        playMusic(0);
        gameState = playState; // set the game state to play state
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
//                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }



        }
    }

    public void update() {
        if(gameState == playState) {
            player.update();
        }
        //UPDATE NPC
        for(int i = 0; i < npc.length; i++) {
            if(npc[i] != null) {
                npc[i].update();
            }
        }
        if(gameState == pauseState) {
            // nothing
        }
    }

    public void paintComponent(Graphics g) {


        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        long drawStart = 0;
        if(keyH.checkDrawTime == true) {
        drawStart = System.nanoTime();

        }

        //TILE
        tileM.draw(g2);

        //OBJECTS
        for (SuperObject objTile : obj) {
            if (objTile != null) {
                objTile.draw(g2, this);
            }
        }
        //NPC
        for (Entity npcEntity : npc) {
            if (npcEntity != null) {
                npcEntity.draw(g2);
            }
        }

        //PLAYER
        player.draw(g2);

        ui.draw(g2);
        if(keyH.checkDrawTime){

        long drawEnd = System.nanoTime();
        long passed = drawEnd - drawStart;
        g2.setColor(Color.white);
        g2.drawString("Draw Time: " + passed, 10, 400);
        System.out.println("Draw Time: " + passed);
        }

        g2.dispose();
    }


    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }

}
