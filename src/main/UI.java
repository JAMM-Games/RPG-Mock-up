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
    public String currentDialogue = "";
    public int commandNum = 0; // for menu navigation

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

        //Title state
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }

        //play state
        if(gp.gameState == gp.playState) {

        }
        //pause state
        if(gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }
        //dialogue state
        if(gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
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
    public void drawTitleScreen(){
        g2.setColor(new Color(130, 200, 229));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        //Title
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String text = "WYNDEL";
        int x = getXForCenteredText(text);
        int y = gp.tileSize * 3;

        //shadow
        g2.setColor(Color.gray);
        g2.drawString(text, x + 5, y + 5);
        //main color of title
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
        //image of character
        x = gp.screenWidth/2 - (gp.tileSize*2)/2;
        y += gp.tileSize*2;
        g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);

        //MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

        text = "NEW GAME";
        x = getXForCenteredText(text);
        y += gp.tileSize * 3.5;
        g2.drawString(text, x, y);
        if(commandNum == 0) {
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "LOAD GAME";
        x = getXForCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 1) {
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "QUIT";
        x = getXForCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 2) {
            g2.drawString(">", x-gp.tileSize, y);
        }

    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80));
        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gp.screenHeight*3;

        g2.drawString(text, x, y);
    }
    public void drawDialogueScreen() {
        //window
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;
        drawSubWindow(x, y, width, height);

        x = gp.tileSize * 3;
        y = gp.tileSize + gp.tileSize / 2;
        g2.drawString(currentDialogue, x, y);

    }
    public void drawSubWindow(int x, int y, int width, int height){
        Color c = new Color(0, 0, 0, 200); // semi-transparent black
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);


        g2.setStroke(new BasicStroke(5));
        g2.setColor(Color.WHITE);
        g2.drawRoundRect(x, y, width, height, 35, 35);

    }

    public int getXForCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth/2 - length/2;
    }

}
