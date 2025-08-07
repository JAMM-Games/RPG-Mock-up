package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {

    GamePanel gp;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, stand, back;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public BufferedImage image, image2, image3;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // Default size for solid area
    public Rectangle hitBox = new Rectangle(0, 0, 0, 0); // Default size for hitbox
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collision = false; // default value for collision is false
    String[] dialogue = new String[20]; // Array to hold dialogue lines

    //STATE
    public int worldX, worldY;
    public String direction = "down"; // Default direction is down
    public int spriteNum = 1;
    public int dialogueIndex = 0; // Index to track current dialogue line
    public boolean collisionOn = false;
    public boolean invincible = false;
    public boolean attacking = false; // Default value for attacking is false
    public boolean alive = true; // Default value for alive is true
    public boolean dying = false; // Default value for dying is false

    //COUNTERS
    public int spriteCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    int dyingCounter = 0; // Counter for dying animation

    //CHARACTER STATS
    public int type; // 0: player, 1: npc, 2: monster
    public String name;
    public int speed;
    public int maxLife;
    public int life;

    public Entity(GamePanel gp){
        this.gp = gp;
    }

    public void setAction() {

    }
    public void speak(){}

    public void update(){

        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if(this.type == 2 && contactPlayer) {
            if(!gp.player.invincible) {
                gp.player.life--;
                gp.player.invincible = true;
                gp.player.invincibleCounter = 0;
            }
        }

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

        spriteCounter++;
            if(spriteCounter > 15) {
            if(spriteNum == 1) {
                spriteNum = 2;
            }else if (spriteNum == 2){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
        if(invincible == true) {
            invincibleCounter++;
            if(invincibleCounter > 40) { // 60 frames = 1 second
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void draw (Graphics2D g2){

        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // Apply same camera edge logic as TileManager
        if(gp.player.screenX > gp.player.worldX) {
            screenX = worldX;
        }
        if(gp.player.screenY > gp.player.worldY) {
            screenY = worldY;
        }
        int rightLimit = gp.screenWidth - gp.player.screenX;
        if(rightLimit > gp.worldWidth - gp.player.worldX) {
            screenX = gp.screenWidth - (gp.worldWidth - worldX);
        }
        int bottomLimit = gp.screenHeight - gp.player.screenY;
        if(bottomLimit > gp.worldHeight - gp.player.worldY){
            screenY = gp.screenHeight - (gp.worldHeight - worldY);
        }

        if(screenX + gp.tileSize > 0 && screenX < gp.screenWidth &&
                screenY + gp.tileSize > 0 && screenY < gp.screenHeight) {

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
                case "stand":
                    image = stand;
                    break;
                case "back":
                    image = back;
                    break;
            }
            if(invincible == true) {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            }
            if(dying == true){
                dyingAnimation(g2);
            }
            g2.drawImage(image, screenX, screenY,null);
            // Only draw the tile if it is within the player's view

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }

    public void dyingAnimation(Graphics2D g2){

        dyingCounter++;
        if(dyingCounter <= 5) {
            changeAlpha(g2, 1f);
        }
        if(dyingCounter > 5 && dyingCounter <= 10) {
            changeAlpha(g2, 0f);
        }
        if(dyingCounter > 10 && dyingCounter <= 15) {
            changeAlpha(g2, 1f);
        }
        if(dyingCounter > 15 && dyingCounter <= 20) {
            changeAlpha(g2, 0f);
        }
        if(dyingCounter > 20 && dyingCounter <= 25) {
            changeAlpha(g2, 1f);
        }
        if(dyingCounter > 25 && dyingCounter <= 30) {
            changeAlpha(g2, 0f);
        }
        if(dyingCounter > 30 && dyingCounter <= 35) {
            changeAlpha(g2, 1f);
        }
        if(dyingCounter > 35) {
            alive = false;
            dying = false;
        }
    }
    public void changeAlpha(Graphics2D g2, float alpha) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
    }

    public BufferedImage setup (String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

}
