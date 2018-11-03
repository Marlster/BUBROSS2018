package stacs.hackthebubble.cookie.entities;

import java.awt.Graphics2D;
import stacs.hackthebubble.cookie.events.Listener;
import stacs.hackthebubble.cookie.graphics.Screen;

public interface Renderable extends Listener {

    /**
     * Should return if this entity is to be rendered. If false, the screen should skip over the object instead of drawing it to the screen
     *
     * @return if this entity should be rendered
     */
    boolean isRendered();

    /**
     * Render this entity to the screen using either the graphics object or the utility functions provided in screen
     *
     * @param g      the graphics object providing direct access to the output
     * @param screen the screen renderer who is calling this render and provides utility functions
     */
    void render(Graphics2D g, Screen screen);

}
