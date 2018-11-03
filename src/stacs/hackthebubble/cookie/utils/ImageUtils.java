package stacs.hackthebubble.cookie.utils;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ImageUtils {

    /**
     * Scale the given image y the given scale factor
     *
     * @param image       the image to scale down
     * @param scaleFactor the multiple of the width and height to use
     * @return the scaled image
     */
    public static BufferedImage scale(BufferedImage image, float scaleFactor) {
        BufferedImage scaled = new BufferedImage((int) (image.getWidth() * scaleFactor), (int) (image.getHeight() * scaleFactor), image.getType());
        Graphics g = scaled.createGraphics();
        g.drawImage(image, 0, 0, scaled.getWidth(), scaled.getHeight(), null);
        g.dispose();
        return scaled;
    }

}
