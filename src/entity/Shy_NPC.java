package entity;

import main.GamePanel;

import java.util.Random;

public class Shy_NPC extends Entity{

    public Shy_NPC(GamePanel gp) {
        super(gp);

        direction = "stand";
        speed = 3;
        getImage();
    }

    public void getImage() {

        stand = setup("/npc/shy_stand");
        back = setup("/npc/shy_back");
        up1 = setup("/npc/shy_up1");
        up2 = setup("/npc/shy_up2");
        down1 = setup("/npc/shy_down1");
        down2 = setup("/npc/shy_down2");
        left1 = setup("/npc/shy_left");
        right1 = setup("/npc/shy_right");
    }

    public void setAction(){

        actionLockCounter++;

        if(actionLockCounter == 120) {

            Random random =  new Random();
            int i = random.nextInt(100) + 1; // generate number from 1 to 100

            if(i <= 25){
                direction = "up";
            } else if(i <= 50) {
                direction = "down";
            } else if(i <= 75) {
                direction = "left";
            } else {
                direction = "right";
            }
            actionLockCounter = 0;
        }

    }

}
