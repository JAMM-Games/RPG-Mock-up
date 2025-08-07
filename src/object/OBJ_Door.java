package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;

public class OBJ_Door extends Entity {

    public OBJ_Door(GamePanel gp) {
        super(gp);

        name = "Door";
        down1 = setup("/objects/wood-door", gp.tileSize, gp.tileSize);

        collision = true;
    }
}
