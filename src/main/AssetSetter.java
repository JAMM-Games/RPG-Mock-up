package main;

import object.OBJ_Boots;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;

    }

    public void setObject() {
        gp.obj[0] = new OBJ_Door(gp);
        gp.obj[0].worldX = 23 * gp.tileSize; // set the x position of the door
        gp.obj[0].worldY = 7 * gp.tileSize; // set the y position of the door

        gp.obj[1] = new OBJ_Key(gp);
        gp.obj[1].worldX = 32 * gp.tileSize; // set the x position of the key
        gp.obj[1].worldY = 17 * gp.tileSize; // set the y position of the key

        gp.obj[2] = new OBJ_Boots(gp);
        gp.obj[2].worldX = 23 * gp.tileSize; // set the x position of the key
        gp.obj[2].worldY = 23 * gp.tileSize; // set the y position of the key
    }

    public void setNPC() {
        // This method can be used to set NPCs in the future
        // Currently, it is not implemented
        gp.npc[0] = new entity.Shy_NPC(gp);
        gp.npc[0].worldX = gp.tileSize*25;
        gp.npc[0].worldY = gp.tileSize*25;
    }
}
