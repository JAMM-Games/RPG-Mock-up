package monster;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class MERCURY_slime extends Entity {
    GamePanel gp;
    public MERCURY_slime(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = 2;
        name = "Mercury slime";
        speed = 1;
        maxLife = 4;
        life = maxLife;

        solidArea.x = 3;
        solidArea.y = 10;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {
        up1 = setup("/slimes/slime-up", gp.tileSize, gp.tileSize);
        up2 = setup("/slimes/slime-up", gp.tileSize, gp.tileSize);
        down1 = setup("/slimes/slime-down", gp.tileSize, gp.tileSize);
        down2 = setup("/slimes/slime-down2", gp.tileSize, gp.tileSize);
        left1 = setup("/slimes/slime-left", gp.tileSize, gp.tileSize);
        left2 = setup("/slimes/slime-left2", gp.tileSize, gp.tileSize);
        right1 = setup("/slimes/slime-right", gp.tileSize, gp.tileSize);
        right2 = setup("/slimes/slime-right2", gp.tileSize, gp.tileSize);
    }
    public void setAction() {
        actionLockCounter++;

        if(actionLockCounter == 180) {

            Random random =  new Random();
            int i = random.nextInt(100) + 1; // generate number from 1 to 100

            if(i <= 25){
                direction = "up";
            }
            if(i > 25 && i <= 50) {
                direction = "down";
            }
            if(i > 50 && i <= 75) {
                direction = "left";
            }
            if(i > 75 && i <= 100) {
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }
}
