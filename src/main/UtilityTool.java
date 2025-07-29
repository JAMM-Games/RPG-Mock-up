package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UtilityTool {

    public BufferedImage scaleImage(BufferedImage original, int width, int height) {
        int imageType = original.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : original.getType();
        BufferedImage scaledImage = new BufferedImage(width, height, imageType);
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null);
        g2.dispose();

        return scaledImage;
    }
}
