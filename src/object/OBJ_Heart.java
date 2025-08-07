package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;

public class OBJ_Heart extends Entity {
    public OBJ_Heart(GamePanel gp) {
        super(gp);

        name = "Heart";
        image = setup("/objects/Heart", gp.tileSize, gp.tileSize);
        image2 = setup("/objects/Empty-heart", gp.tileSize, gp.tileSize);
    }
}
