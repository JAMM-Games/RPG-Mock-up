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

        hitBox.width = 40;
        hitBox.height = 40;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();

    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23; // set initial worldX position
        worldY = gp.tileSize * 21; // set initial worldY position
        speed = 4;
        direction = "down";

        // PLAYER STATS
        maxLife = 3;
        life = maxLife;
    }

    public void getPlayerImage() {

        up1 = setup("/player/up#1", gp.tileSize, gp.tileSize);
        up2 = setup("/player/up#2", gp.tileSize, gp.tileSize);
        down1 = setup("/player/down#1", gp.tileSize, gp.tileSize);
        down2 = setup("/player/down#2", gp.tileSize, gp.tileSize);
        left1 = setup("/player/left#1", gp.tileSize, gp.tileSize);
        left2 = setup("/player/left#2", gp.tileSize, gp.tileSize);
        right1 = setup("/player/right#1", gp.tileSize, gp.tileSize);
        right2 = setup("/player/right#2", gp.tileSize, gp.tileSize);
    }

    public void getPlayerAttackImage() {
        attackUp1 = setup("/player/attack_up", gp.tileSize, gp.tileSize * 2);
        attackUp2 = setup("/player/attack_up", gp.tileSize, gp.tileSize * 2);
        attackDown1 = setup("/player/attack_down", gp.tileSize, gp.tileSize * 2);
        attackDown2 = setup("/player/attack_down", gp.tileSize, gp.tileSize * 2);
        attackLeft1 = setup("/player/attack_left", gp.tileSize * 2, gp.tileSize);
        attackLeft2 = setup("/player/attack_left", gp.tileSize * 2, gp.tileSize);
        attackRight1 = setup("/player/attack_right", gp.tileSize * 2, gp.tileSize);
        attackRight2 = setup("/player/attack_right", gp.tileSize * 2, gp.tileSize);
    }

    public void update() {

        boolean moving = keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.enterPressed;
        if(attacking){
            playerAttacking();
        }
        else if(moving) {
            if (keyH.upPressed) direction = "up";
            else if (keyH.downPressed) direction = "down";
            else if (keyH.leftPressed) direction = "left";
            else if (keyH.rightPressed) direction = "right";

            //CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this); // check for collision with tiles

            //CHECK  OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            //CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            //CHECK MONSTER COLLISION
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            //CHECK EVENT
            gp.eHandler.checkEvent(gp.currentMap);


            //IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (!collisionOn && !keyH.enterPressed) {
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

            gp.keyH.enterPressed = false;

            spriteCounter++;
            if (spriteCounter > 15) {
               if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
               }
                spriteCounter = 0;
            }
        }

        if(invincible == true) {
            invincibleCounter++;
            if(invincibleCounter > 60) { // 60 frames = 1 second
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void playerAttacking() {

        spriteCounter++;

        if(spriteCounter <= 5) {
            spriteNum = 1;
        }
        if(spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;

            //save current position
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            //adjust player position for attack
            switch (direction){
                case "up":
                    worldY -= hitBox.height;
                    break;
                case "down":
                    worldY += hitBox.height;
                    break;
                case "left":
                    worldX -= hitBox.width;
                    break;
                case "right":
                    worldX += hitBox.width;
                    break;
            }
            //hitbox becomes solid area
            solidArea.width = hitBox.width;
            solidArea.height = hitBox.height;

            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex);

            //reset player position
            worldX = currentWorldX; //reset player position
            worldY = currentWorldY; //reset player position
            solidArea.width = solidAreaWidth; //reset solid area width
            solidArea.height = solidAreaHeight; //reset solid area height

        }
        if(spriteCounter > 25) {
            spriteNum = 1;
            attacking = false;
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
                        gp.playMusic(2);
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

        if(gp.keyH.enterPressed){

            if(i != 999) {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }else {
                attacking = true;
            }
        }
    }

    public void contactMonster(int i) {

        if(i != 999) {

            if(invincible == false) {
            life -= 1;
            invincible = true;
            }
        }
    }

    public void damageMonster(int i) {
        if(i != 999) {
            if(gp.monster[i].invincible == false) {
                gp.monster[i].life -= 1;
                gp.monster[i].invincible = true;
                gp.monster[i].invincibleCounter = 0;

                if(gp.monster[i].life <= 0) {
                    gp.monster[i] = null; // monster is dead for animation monster[i].dying = true;
                }
            }
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch (direction) {
            case "up":
                if(attacking == false){
                    if(spriteNum == 1) image = up1;
                    if(spriteNum == 2) image = up2;
                } else {
                    if(spriteNum == 1) image = attackUp1;
                    if(spriteNum == 2) image = attackUp2;
                }
                break;
            case "down":
                if(attacking == false){
                    if(spriteNum == 1) image = down1;
                    if(spriteNum == 2) image = down2;
                } else {
                    if(spriteNum == 1) image = attackDown1;
                    if(spriteNum == 2) image = attackDown2;
                }
                break;
            case "left":
                if(attacking == false){
                    if(spriteNum == 1) image = left1;
                    if(spriteNum == 2) image = left2;
                } else {
                    if(spriteNum == 1) image = attackLeft1;
                    if(spriteNum == 2) image = attackLeft2;
                }
                break;
            case "right":
                if(attacking == false){
                    if(spriteNum == 1) image = right1;
                    if(spriteNum == 2) image = right2;
                } else {
                    if(spriteNum == 1) image = attackRight1;
                    if(spriteNum == 2) image = attackRight2;
                }
                break;
        }
        int x = screenX;
        int y = screenY;

        if(screenX > worldX) {
            x = worldX;
        }
        if(screenY > worldY) {
            y = worldY;
        }

        int rightLimit = gp.screenWidth - screenX;
        if(rightLimit > gp.worldWidth - worldX) {
            x = gp.screenWidth - (gp.worldWidth - worldX);
        }
        int bottomLimit = gp.screenHeight - screenY;
        if(bottomLimit > gp.worldHeight - worldY){
            y = gp.screenHeight - (gp.worldHeight - worldY);
        }

        if(invincible == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }
        g2.drawImage(image, x, y,null);
        //reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));


        //debug
        g2.setFont(new Font("Arial", Font.PLAIN, 26));
        g2.setColor(Color.white);
        g2.drawString("invicible: " + invincibleCounter, 10, 400);
    }
}
