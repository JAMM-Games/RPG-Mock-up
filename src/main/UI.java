package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B;
//    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 20);
        arial_80B = new Font("Arial", Font.BOLD, 80);
//        OBJ_Key key = new OBJ_Key(gp);
//        keyImage = key.image;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);

        if(gp.gameState == gp.playState) {

        }
        if(gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }

//
//        if(gameFinished){
//
//            g2.setFont(arial_40);
//            g2.setColor(Color.WHITE);
//
//            String text;
//            int textLength;
//            int x;
//            int y;
//
//            text = "You escaped!";
//            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
//            x = gp.screenWidth/2 - textLength/2;
//            y = gp.screenHeight/2 - (gp.tileSize*3);
//            g2.drawString(text, x, y);
//
//            g2.setFont(arial_80B);
//            g2.setColor(Color.YELLOW);
//            text = "Congratulations!";
//            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
//            x = gp.screenWidth/2 - textLength/2;
//            y = gp.screenHeight/2 + (gp.tileSize*2);
//            g2.drawString(text, x, y);
//
//            gp.gameThread = null;
//
//
//        }else {
//            g2.setFont(arial_40);
//            g2.setColor(Color.WHITE);
//            g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
//
//            // Display player information
////        g2.drawString("Player X: " + gp.player.worldX, 25, 30);
////        g2.drawString("Player Y: " + gp.player.worldY, 25, 50);
//
//            // Display player speed
////        g2.drawString("Speed: " + gp.player.speed, 25, 70);
//
//            // Display keys collected
//            g2.drawString("x " + gp.player.hasKey, 65, 55);
//
//            // Display FPS
//            g2.drawString("FPS: " + gp.FPS, 25, 110);
//
//            if (messageOn) {
//                g2.setFont(g2.getFont().deriveFont(30F));
//                g2.drawString(message, gp.tileSize * 5, gp.tileSize * 5);
//
//                messageCounter++;
//
//                if (messageCounter > 60) { // message will disappear after 2 seconds
//                    messageCounter = 0;
//                    messageOn = false;
//                }
//            }
//        }
    }
    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80));
        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);
    }
    public int getXForCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth/2 - length/2;
    }

}
