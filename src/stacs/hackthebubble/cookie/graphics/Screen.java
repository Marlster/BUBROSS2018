package stacs.hackthebubble.cookie.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;
import stacs.hackthebubble.cookie.events.EventEmitter;

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
}
