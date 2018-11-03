package stacs.hackthebubble.cookie.graphics.sprites;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;

public class Sprite {

    /**
     * The actual image that this sprite represents
     */
    private BufferedImage image;
    /**
     * The width of sprite, acts a quicker way to access {@link BufferedImage#getWidth()} via {@link #getImage()}
     */
    private int width;
    /**
     * The height of sprite, acts a quicker way to access {@link BufferedImage#getHeight()} via {@link #getImage()}
     */
    private int height;

    /**
     * Constructs a new sprite with the buffered image, automatically populating the width and height
     *
     * @param image the image that this sprite should represent
     */
    public Sprite(BufferedImage image) {
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    /**
     * Constructs a new sprite using the {@link #Sprite(BufferedImage)} function using {@link ImageIO#read(InputStream)} to actaully read the image. Must be a valid
     * image input stream or an {@link IOException} will be raised by the read function.
     *
     * @param stream a valid image stream
     * @throws IOException if there is an error reading from the stream ({@link ImageIO#read(URL)})
     */
    public Sprite(InputStream stream) throws IOException {
        this(ImageIO.read(stream));
    }

    /**
     * Constructs a new sprite using the {@link #Sprite(InputStream)} function using {@link Class#getResourceAsStream(String)} to generate the required
     * input stream. This must be a valid resource path or an {@link IOException} will be raised. An exception will also be raised if the file cannot be loaded for any reason
     *
     * @param resourcePath a valid resource location from which an image file can be loaded.
     * @throws IOException if there is an error reading the file
     */
    public Sprite(String resourcePath) throws IOException {
        this(ImageIO.read(Sprite.class.getResourceAsStream(resourcePath)));
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
