package main;

import entity.Entity;
import object.OBJ_Heart;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B;
//    BufferedImage keyImage;
    BufferedImage heartFull, heartEmpty;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0; // for menu navigation
    public int subState = 0; // for options menu

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 20);
        arial_80B = new Font("Arial", Font.BOLD, 80);
//        OBJ_Key key = new OBJ_Key(gp);
//        keyImage = key.image;

        //CHAR HUD
        Entity heart = new OBJ_Heart(gp);
        heartFull = heart.image;
        heartEmpty = heart.image2;
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
            drawPlayerLife();

        }
        //pause state
        if(gp.gameState == gp.pauseState) {
            drawPauseScreen();
            drawPlayerLife();
        }
        //dialogue state
        if(gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
        }
        if(gp.gameState == gp.optionsState) {
            drawOptionsScreen();
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

    public void drawPlayerLife() {
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;
        // Draw max hearts
        while(i < gp.player.maxLife) {
            g2.drawImage(heartEmpty, x, y, null);
            i++;
            x += gp.tileSize;
        }
        // reset
        x = gp.tileSize / 2;
        y = gp.tileSize / 2;
        i = 0;
        // Draw full heart
        while(i < gp.player.life) {
            g2.drawImage(heartFull, x, y, null);
            i++;
            x += gp.tileSize;
        }
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

    public void drawOptionsScreen() {

        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(16F));

        int frameX = gp.tileSize * 6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState){
            case 0: options_top(frameX, frameY); break;
            case 1: break;
            case 2: options_control(frameX,frameY); break;
            case 3: options_quitComfirmation(frameX, frameY); break;
        }
        gp.keyH.enterPressed = false; // reset the enter key press after drawing the options screen
    }
    public void options_top(int frameX, int frameY){
            int textX;
            int textY;

            String text = "OPTIONS";
            textX = getXForCenteredText(text);
            textY = frameY + gp.tileSize;
            g2.drawString(text, textX, textY);

            //Full Screen ON/OFF
            textX = frameX + gp.tileSize;
            textY += gp.tileSize * 2;
            g2.drawString("FULL SCREEN", textX, textY);
            if(commandNum == 0) {
                g2.drawString(">", textX - 25, textY);
                if(gp.keyH.enterPressed){
                    if(gp.fullScreenOn == false) {
                        gp.fullScreenOn = true;
                        gp.setFullScreen();
                    } else {
                        gp.fullScreenOn = false;
                    }
                }
            }

            //MUSIC
            textY += gp.tileSize;
            g2.drawString("MUSIC", textX, textY);
            if(commandNum == 1) {
                g2.drawString(">", textX - 25, textY);
            }

            //SOUND EFFECTS
            textY += gp.tileSize;
            g2.drawString("SE", textX, textY);
            if(commandNum == 2) {
                g2.drawString(">", textX - 25, textY);
            }

            //CONTROL
            textY += gp.tileSize;
            g2.drawString("CONTROL", textX, textY);
            if(commandNum == 3) {
                g2.drawString(">", textX - 25, textY);
                if (gp.keyH.enterPressed){
                    subState = 2; // go to control options
                    commandNum = 0; // reset command number
                }
            }

            //SAVE
            textY += gp.tileSize;
            g2.drawString("SAVE", textX, textY);
            if(commandNum == 4) {
                g2.drawString(">", textX - 25, textY);
            }

            //QUIT
            textY += gp.tileSize * 2;
            g2.drawString("QUIT", textX, textY);
            if(commandNum == 5) {
                g2.drawString(">", textX - 25, textY);
                if(gp.keyH.enterPressed){
                    subState = 3;
                    commandNum = 0; // reset command number
                }
            }

            //FULL SCREEN CHECK BOX
            textX = frameX + (int)(gp.tileSize * 4.5);
            textY = frameY + gp.tileSize * 2 + 30;
            g2.setStroke(new BasicStroke(3));
            g2.drawRect(textX, textY, 24, 24);
            if(gp.fullScreenOn){
                g2.fillRect(textX, textY, 24, 24);
            }

            //MUSIC SLIDER
            textY += gp.tileSize;
            g2.drawRect(textX, textY, 120, 24);
            int volumeWidth = 24 * gp.music.volumeScale;
            g2.fillRect(textX, textY, volumeWidth, 24);

            //SE SLIDER
            textY += gp.tileSize;
            g2.drawRect(textX, textY, 120, 24);
            volumeWidth = 24 * gp.se.volumeScale;
            g2.fillRect(textX, textY, volumeWidth, 24);

    }

    public void options_control(int frameX, int frameY){

        int textX;
        int textY;
        String text = "CONTROL";

        textX = getXForCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.tileSize;
        textY += gp.tileSize;

        g2.drawString("Move", textX, textY); textY += gp.tileSize;
        g2.drawString("Interact/Attack", textX, textY); textY += gp.tileSize;
        g2.drawString("Pause", textX, textY); textY += gp.tileSize;
        g2.drawString("Options", textX, textY); textY += gp.tileSize;

        textX = frameX + gp.tileSize*6;
        textY = frameY + gp.tileSize * 2;

        g2.drawString("WASD", textX, textY); textY += gp.tileSize;
        g2.drawString("ENTER", textX, textY); textY += gp.tileSize;
        g2.drawString("P", textX, textY); textY += gp.tileSize;
        g2.drawString("ESC", textX, textY); textY += gp.tileSize;

        //BACK
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize * 9;
        g2.drawString("BACK", textX, textY);
        if(commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed){
                subState = 0; // go back to main options
            }
        }
    }

    public void options_quitComfirmation(int frameX, int frameY){

        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;

        currentDialogue = "Are you sure you want to quit?";
        g2.drawString(currentDialogue, textX, textY);

        //Yes
        String text = "YES";
        textX = getXForCenteredText(text);
        textY += gp.tileSize * 3;
        g2.drawString(text, textX, textY);
        if(commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed){
                subState = 0;
                gp.gameState = gp.titleState; // go back to title screen
            }
        }
        //No
        text = "NO";
        textX = getXForCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum == 1) {
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed){
                subState = 0; // go back to main options
                commandNum = 5; // reset command number
            }
        }
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
