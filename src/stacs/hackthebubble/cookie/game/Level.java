package stacs.hackthebubble.cookie.game;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import stacs.hackthebubble.cookie.entities.Entity;
import stacs.hackthebubble.cookie.entities.Renderable;
import stacs.hackthebubble.cookie.graphics.Screen;
import stacs.hackthebubble.cookie.graphics.sprites.Sprite;
import stacs.hackthebubble.cookie.location.Coordinate;

public class Level implements Renderable {

    /**
     * The backdrop for this level
     */
    private Sprite sprite;
    /**
     * A human readable name for the level
     */
    private String name;

    /**
     * The entities bound to the level. These should be things like platforms, items and other things that are locked to the level and not the game state.
     */
    private List<Entity> entities = new ArrayList<>();

    /**
     * Constructs a new level with the given sprite for the backdrop and the name
     *
     * @param sprite the sprite backdrop
     * @param name   the name
     */
    public Level(Sprite sprite, String name) {
        this.sprite = sprite;
        this.name = name;
    }

    /**
     * Ads the given entity to the level entities which will be rendered when the level renders
     *
     * @param entity the entity to add
     * @return if the entity was added was successfully
     */
    public boolean addEntity(Entity entity) {
        return entities.add(entity);
    }

    /**
     * Ads the given entities to the level entities which will be rendered when the level renders
     *
     * @param entities the entities to add
     * @return if the entity set was added was successfully
     */
    public boolean addEntities(Entity... entities) {
        return addEntities(Arrays.asList(entities));
    }

    /**
     * Ads the given entities to the level entities which will be rendered when the level renders
     *
     * @param entities the entities to add
     * @return if the entity set was added was successfully
     */
    public boolean addEntities(Collection<Entity> entities) {
        return this.entities.addAll(entities);
    }

    /**
     * Remove the given entity from the level set
     *
     * @param entity the entity to remove
     * @return if the entity was removed successfully
     */
    public boolean removeEntity(Entity entity) {
        return entities.remove(entity);
    }

    /**
     * Remove the given entities from the level set
     *
     * @param entities the entities to remove
     * @return if the entity set was removed successfully
     */
    public boolean removeEntities(Entity... entities) {
        return removeEntities(Arrays.asList(entities));
    }

    /**
     * Remove the given entities from the level set
     *
     * @param entities the entities to remove
     * @return if the entity set was removed successfully
     */
    public boolean removeEntities(Collection<Entity> entities) {
        return this.entities.removeAll(entities);
    }

    @Override
    public boolean isRendered() {
        return true;
    }

    @Override
    public void render(Graphics2D g, Screen screen) {
        screen.renderSprite(sprite, new Coordinate(), g);
        entities.forEach(e -> e.render(g, screen));
    }

    @Override
    public void onEvent(String event, Object... data) {
    }

    public String getName() {
        return name;
    }
}
