package main;

import java.awt.*;

public class EventHandler {

    GamePanel gp;
    Rectangle eventRect;
    int eventRectDefaultX, eventRectDefaultY;

    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventRect = new Rectangle();
        eventRect.x = 23;
        eventRect.y = 23;
        eventRect.width = 2;
        eventRect.height = 2;
        eventRectDefaultX = eventRect.x;
        eventRectDefaultY = eventRect.y;

    }

    public void checkEvent(int currentMap) {
        // Check if the player is in the event area
        if(currentMap == 0 && hit(11, 30, "left")) {
            damagePit(gp.dialogueState);
        }
        if((currentMap == 1 && hit(21, 24, "right")) ||
                (currentMap == 1 && hit(21, 25, "right"))) {
            // Check if the player is in the healing water area
            healingWater(gp.dialogueState);

        }
    }

    public boolean hit(int eventCol, int eventRow, String reqDirection) {
        boolean hit = false;
        // Get the player's solid area position
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        // Get the event rectangle position
        eventRect.x = eventCol * gp.tileSize + eventRect.x;
        eventRect.y = eventRow * gp.tileSize + eventRect.y;

        if (gp.player.solidArea.intersects(eventRect)) {
            if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                hit = true;
            }
        }

        // Reset the solid area and event rectangle positions
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect.x = eventRectDefaultX;
        eventRect.y = eventRectDefaultY;

        return hit;
    }
    public void damagePit(int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "The area is Toxic!";
        gp.player.life -= 1;
    }
    public void healingWater(int gameState) {

        if(gp.keyH.enterPressed){
            gp.gameState = gameState;
            gp.ui.currentDialogue = "You drank the Holy Water." +
                    "\nHP recovered!";
            gp.player.life = gp.player.maxLife;
        }
        else {
            gp.gameState = gameState;
            gp.ui.currentDialogue = "You are in the healing water area." +
                    "\nPress Enter to drink.";
        }
    }
}
