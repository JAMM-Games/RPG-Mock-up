package entity;

import main.GamePanel;

import java.util.Random;

public class Shy_NPC extends Entity{

    public Shy_NPC(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 2;

        getImage();
        getDialogue();
    }

    public void getImage() {

        up1 = setup("/npc/shy_up1");
        up2 = setup("/npc/shy_up2");
        down1 = setup("/npc/shy_down1");
        down2 = setup("/npc/shy_down2");
        left1 = setup("/npc/shy_left");
        left2 = setup("/npc/shy_left");
        right1 = setup("/npc/shy_right");
        right2 = setup("/npc/shy_right");
    }

    public void getDialogue() {
        dialogue[0] = "Dont talk to me, I'm shy...";
        dialogue[1] = "Why have you come here";
        dialogue[2] = "Wait...";
        dialogue[3] = "You're Roy aren't you?! Get to the city right away!";
    }

    public void setAction(){

        actionLockCounter++;

        if(actionLockCounter == 120) {

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

    public void speak(){
        if(dialogue[dialogueIndex] == null){
            dialogueIndex = 3;
        }
        gp.ui.currentDialogue = dialogue[dialogueIndex];
        dialogueIndex++;

        switch(gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
            default:
                direction = "stand";
        }
    }

}
