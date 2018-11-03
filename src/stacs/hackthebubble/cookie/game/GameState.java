package stacs.hackthebubble.cookie.game;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import stacs.hackthebubble.cookie.entities.Entity;
import stacs.hackthebubble.cookie.events.EventEmitter;
import stacs.hackthebubble.cookie.events.EventEmitter.EventConstant;
import stacs.hackthebubble.cookie.events.Listener;
import stacs.hackthebubble.cookie.graphics.Screen;

public class GameState implements Listener {

    /**
     * Contains the current game state being run by the program. This should only be updated when something major changes such as a level change
     */
    private static GameState currentlyActiveState;
    /**
     * The lock object guarding access to the non-final {@link #currentlyActiveState}.
     */
    private static final Object activeLockObject = new Object();

    /**
     * Sets the new game state. This is synchronised about {@link #activeLockObject} to ensure safe concurrent access
     *
     * @param newState the new game state
     */
    public static void setActiveGameState(GameState newState) {
        synchronized (activeLockObject) {
            currentlyActiveState = newState;
        }
    }

    /**
     * Returns the current game state. This is synchronised about {@link #activeLockObject} to ensure safe concurrent access
     *
     * @return the current game state
     */
    public static GameState getActiveGameState() {
        synchronized (activeLockObject) {
            return currentlyActiveState;
        }
    }

    /**
     * The level currently being played on
     */
    private Level level;
    /**
     * The entities currently contained within the game
     */
    private final List<Entity> entities = new CopyOnWriteArrayList<>();

    /**
     * Constructs a game with the given level ready for rendering and adds the given entities to the list of entities in play
     *
     * @param level    the level currently in play
     * @param entities the set of entities that should be added to begin with
     */
    public GameState(Level level, Entity... entities) {
        this.level = level;
        addEntities(entities);

        EventEmitter.subscribe(EventConstant.UPDATE.getEventName(), this);
    }

    /**
     * Renders the level and its associated entities before rendering its own entities
     *
     * @param g      the graphics instance that should be drawn to
     * @param screen the screen that called this render and provides utility functions
     */
    public void render(Graphics2D g, Screen screen) {
        level.render(g, screen);
        synchronized (entities) {
            entities.stream().filter(e -> e.isRendered() && !e.isMarkedForDeletion()).forEach(e -> e.render(g, screen));
        }
    }

    /**
     * Ads the given entity to the game state entities which will be rendered when the game state renders
     *
     * @param entity the entity to add
     * @return if the entity was added was successfully
     */
    public boolean addEntity(Entity entity) {
        synchronized (entities) {
            return entities.add(entity);
        }
    }

    /**
     * Ads the given entities to the game state entities which will be rendered when the game state renders
     *
     * @param entities the entities to add
     * @return if the entity set was added was successfully
     */
    public boolean addEntities(Entity... entities) {
        return addEntities(Arrays.asList(entities));
    }

    /**
     * Ads the given entities to the game state entities which will be rendered when the game state renders
     *
     * @param entities the entities to add
     * @return if the entity set was added was successfully
     */
    public boolean addEntities(Collection<Entity> entities) {
        synchronized (entities) {
            return this.entities.addAll(entities);
        }
    }

    /**
     * Remove the given entity from the game state set
     *
     * @param entity the entity to remove
     * @return if the entity was removed successfully
     */
    public boolean removeEntity(Entity entity) {
        synchronized (entities) {
            return entities.remove(entity);
        }
    }

    /**
     * Remove the given entities from the game state set
     *
     * @param entities the entities to remove
     * @return if the entity set was removed successfully
     */
    public boolean removeEntities(Entity... entities) {
        return removeEntities(Arrays.asList(entities));
    }

    /**
     * Remove the given entities from the game state set
     *
     * @param entities the entities to remove
     * @return if the entity set was removed successfully
     */
    public boolean removeEntities(Collection<Entity> entities) {
        synchronized (entities) {
            return this.entities.removeAll(entities);
        }
    }

    public Level getLevel() {
        return level;
    }

    @Override
    public void onEvent(String event, Object... data) {
        if (EventConstant.UPDATE.isEvent(event)) {
            level.onEvent(event, data);
            synchronized (entities) {
                entities.forEach(e -> e.onEvent(event, data));
            }
        }
    }
}
