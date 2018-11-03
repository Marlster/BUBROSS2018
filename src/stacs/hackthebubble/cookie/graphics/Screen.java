package stacs.hackthebubble.cookie.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.Random;
import stacs.hackthebubble.cookie.events.EventEmitter;
import stacs.hackthebubble.cookie.graphics.sprites.Sprite;
import stacs.hackthebubble.cookie.location.Coordinate;

public class Screen {

    /**
     * The width of the screen that this object is representing. Rendering at positions greater than this will not be performed
     */
    private int width;
    /**
     * The height of the screen that this object is representing. Rendering at positions greater than this will not be performed
     */
    private int height;

    /**
     * Constructs a new screen with the given width and height. This will automatically subscribe the instance to updates
     *
     * @param width  the drawing width
     * @param height the drawing height
     */
    public Screen(int width, int height) {
        this.width = width;
        this.height = height;

        EventEmitter.subscribe("update", (a, b) -> update());
    }

    /**
     * For demonstration purposes only
     */
    private Random random = new Random();

    /**
     * Renders all levels and instances to the given graphics instance
     *
     * @param g the graphics to draw the images to
     */
    public void render(Graphics2D g) {
        for (int y = 0; y < height; y += random.nextInt(15) + 15) {
            for (int x = 0; x < width; x += random.nextInt(15) + 15) {
                g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                g.fillRect(x, y, 30, 30);
            }
        }
    }

    /**
     * Used to coordinate the updating of the entities and levels contained within this screen
     */
    public void update() {

    }

    // Utility functions

    /**
     * Renders the given sprite at the given location using the graphic manager provided
     *
     * @param sprite   the sprite which needs to be drawn
     * @param position the position on the screen that this should be drawn at
     * @param g        the graphics object the sprite should be drawn to
     */
    public void renderSprite(Sprite sprite, Coordinate position, Graphics2D g) {
        g.drawImage(sprite.getImage(), position.getX(), position.getY(), null);
    }

    /**
     * Renders the given sprite, at the location and size provided using the given graphics instance
     *
     * @param sprite   the sprite which needs to be drawn
     * @param position the position on the screen that this should be drawn at
     * @param size     the size at which the sprite should be drawn, this should be multiple if you want it to produce high quality results
     * @param g        the graphics object the sprite should be drawn to
     */
    public void renderSprite(Sprite sprite, Coordinate position, Dimension size, Graphics2D g) {
        g.drawImage(sprite.getImage(), position.getX(), position.getY(), size.width, size.height, null);
    }
}
