package object;

import main.GamePanel;

import javax.imageio.ImageIO;

public class OBJ_Boots extends SuperObject{
    GamePanel gp;
    public OBJ_Boots(GamePanel gp) {
        this.gp = gp;

        name = "Boots";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/basic-boots.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        collision = true;
    }

}
