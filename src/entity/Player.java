package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public int hasKey = 0;

    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - gp.tileSize/2;
        screenY = gp.screenHeight / 2 - gp.tileSize/2;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();

    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23; // set initial worldX position
        worldY = gp.tileSize * 21; // set initial worldY position
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {

        up1 = setup("/player/up#1");
        up2 = setup("/player/up#2");
        down1 = setup("/player/down#1");
        down2 = setup("/player/down#2");
        left1 = setup("/player/left#1");
        left2 = setup("/player/left#2");
        right1 = setup("/player/right#1");
        right2 = setup("/player/right#2");
    }

    public void update() {
        boolean moving = keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed;

        if(moving) {
            if(keyH.upPressed) direction = "up";
            else if(keyH.downPressed) direction = "down";
            else if(keyH.leftPressed) direction = "left";
            else if(keyH.rightPressed) direction = "right";

        //CHECK TILE COLLISION
        collisionOn = false;
        gp.cChecker.checkTile(this); // check for collision with tiles

        //CHECK  OBJECT COLLISION
        int objIndex = gp.cChecker.checkObject(this,true);
        pickUpObject(objIndex);

        //CHECK NPC COLLISION
        int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
        interactNPC(npcIndex);

        //IF COLLISION IS FALSE, PLAYER CAN MOVE
        if(!collisionOn) {
            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
        }
        }

        spriteCounter++;
        if(spriteCounter > 15) {
            if(spriteNum == 1) {
                spriteNum = 2;
            }else if (spriteNum == 2){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }


    public void pickUpObject(int i) {
        if(i != 999) {

            String objectName = gp.obj[i].name;

            switch(objectName) {
                case "Key":
//                    gp.playSE(1);
                    hasKey++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("You picked up a key!");
                    break;
                case "Door":
//                    gp.playSE(2);
                    if(hasKey > 0) {
                        gp.obj[i] = null;
                        hasKey--;
                        gp.ui.showMessage("You opened the door!");
                        gp.ui.gameFinished = true;
                        gp.stopMusic();
                        gp.playMusic(1);
                    }else {
                        gp.ui.showMessage("    You need a key");
                    }
                    break;
                case "Boots":
//                    gp.playSE(3);
                    speed += 1;
                    gp.obj[i] = null;
                    gp.ui.showMessage("        SPEED +1");
                    break;
            }
        }
    }

    public void interactNPC(int i) {
        if(i != 999) {
            System.out.println("youre hitting HIM!");
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch (direction) {
            case "up":
                if(spriteNum == 1) image = up1;
                if(spriteNum == 2) image = up2;
                break;
            case "down":
                if(spriteNum == 1) image = down1;
                if(spriteNum == 2) image = down2;
                break;
            case "left":
                if(spriteNum == 1) image = left1;
                if(spriteNum == 2) image = left2;
                break;
            case "right":
                if(spriteNum == 1) image = right1;
                if(spriteNum == 2) image = right2;
                break;
        }
        g2.drawImage(image, screenX, screenY,null);

    }
}
