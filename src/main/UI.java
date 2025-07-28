package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {

    GamePanel gp;
    Font arial_40;
    BufferedImage keyImage;

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 20);
        OBJ_Key key = new OBJ_Key();
        keyImage = key.image;
    }

    public void draw(Graphics2D g2) {
        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);
        g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);

        // Display player information
//        g2.drawString("Player X: " + gp.player.worldX, 25, 30);
//        g2.drawString("Player Y: " + gp.player.worldY, 25, 50);

        // Display player speed
//        g2.drawString("Speed: " + gp.player.speed, 25, 70);

        // Display keys collected
        g2.drawString("x " + gp.player.hasKey, 65, 55);

        // Display FPS
        g2.drawString("FPS: " + gp.FPS, 25, 110);
    }
}
