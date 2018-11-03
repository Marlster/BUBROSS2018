package stacs.hackthebubble.cookie.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.Random;
import stacs.hackthebubble.cookie.events.EventEmitter;
import stacs.hackthebubble.cookie.game.GameState;
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
     * Renders all levels and instances to the given graphics instance
     *
     * @param g the graphics to draw the images to
     */
    public void render(Graphics2D g) {
        if (GameState.getActiveGameState() != null) {
            GameState.getActiveGameState().render(g, this);
            GameState.getActiveGameState().getLevel().getBounds().forEach(b -> {
                int width = b.getBottomRight().getX() - b.getTopLeft().getX();
                int height = b.getBottomRight().getY() - b.getTopLeft().getY();
                g.setColor(Color.BLUE);
                g.drawRect(b.getTopLeft().getX(), b.getTopLeft().getY(), width, height);
                g.setColor(Color.GREEN);
                g.fillOval(b.getTopLeft().getX() - 5, b.getTopLeft().getY() - 5, 10, 10);
                g.fillOval(b.getBottomRight().getX() - 5, b.getBottomRight().getY() - 5, 10, 10);
            });
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
