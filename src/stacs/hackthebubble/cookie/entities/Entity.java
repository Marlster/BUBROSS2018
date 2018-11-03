package stacs.hackthebubble.cookie.entities;

import java.awt.Graphics2D;
import stacs.hackthebubble.cookie.graphics.Screen;
import stacs.hackthebubble.cookie.graphics.sprites.Sprite;
import stacs.hackthebubble.cookie.location.Coordinate;

public class Entity implements Renderable {

    /**
     * The location on the screen that this entity should be drawn at
     */
    private Coordinate location;
    /**
     * The sprite that should be rendered to represent this entity
     */
    private Sprite sprite;

    /**
     * If the entity should be displayed by the renderer
     */
    private boolean visible;
    /**
     * If the entity should be removed on the next update run by the screen
     */
    private boolean markedForDeletion;

    /**
     * Constructs a new entity that is not marked for deletion and is visible with the given position and sprite
     *
     * @param location the location of the sprite on the render screen (top left [0, 0])
     * @param sprite   the sprite that represents this entity
     */
    public Entity(Coordinate location, Sprite sprite) {
        this(location, sprite, true);
    }

    /**
     * Constructs a new entity that is not marked for deletion, is visible and located at (0, 0) with the given sprite
     *
     * @param sprite the sprite that represents this entity
     */
    public Entity(Sprite sprite) {
        this(new Coordinate(), sprite, true);
    }

    /**
     * Constructs a new entity that is not marked from deletion and has the given sprite, location and visibility status
     *
     * @param location the location of the sprite on the render screen (top left [0, 0])
     * @param sprite   the sprite that represents this entity
     * @param visible  the visibility status of this entity
     */
    public Entity(Coordinate location, Sprite sprite, boolean visible) {
        this.location = location;
        this.sprite = sprite;
        this.visible = visible;
    }

    /**
     * Constructs a new entity that is not marked for deletion, is at (0, 0) and has the given sprite and visibility status
     *
     * @param sprite  the sprite that represents this entity
     * @param visible the visibility status of this entity
     */
    public Entity(Sprite sprite, boolean visible) {
        this(new Coordinate(), sprite, visible);
    }

    @Override
    public boolean isRendered() {
        return visible;
    }

    @Override
    public void render(Graphics2D g, Screen screen) {
        screen.renderSprite(sprite, location, g);
    }

    @Override
    public void onEvent(String event, Object... data) {
    }

    public boolean isMarkedForDeletion() {
        return markedForDeletion;
    }

    /**
     * Sets {@link #markedForDeletion} to true which means it should be deleted from view on the next update run. To remove it from view instantly, first use {@link #hide()}
     * then call this function for proper cleanup
     */
    public void delete() {
        markedForDeletion = true;
    }

    /**
     * Hides the sprite from view by toggling the visibility status so the screen should skip the entity
     */
    public void hide() {
        visible = false;
    }

    /**
     * Shows the sprite by setting the visibility status so that the screen should begin rendering it
     */
    public void show() {
        visible = true;
    }

    public Coordinate getLocation() {
        return location;
    }

    public Sprite getSprite() {
        return sprite;
    }
}
