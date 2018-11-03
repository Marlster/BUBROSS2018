package stacs.hackthebubble.cookie.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import stacs.hackthebubble.cookie.graphics.Screen;
import stacs.hackthebubble.cookie.graphics.sprites.Sprite;
import stacs.hackthebubble.cookie.location.Coordinate;

public class LivingEntity extends Entity {

    /**
     * The current health of the entity. It is upper bounded by {@link #maxHealth} but has no lower bound to allow for more complex death mechanics but death is declared
     * at <= 0 unless overwritten
     */
    private int health;
    /**
     * The maximum possible health of the entity. -1 means no maximum
     */
    private int maxHealth;

    public LivingEntity(Coordinate location, Sprite sprite, int health, int maxHealth) {
        super(location, sprite);
        this.health = health;
        this.maxHealth = maxHealth;
    }

    public LivingEntity(Sprite sprite, int health, int maxHealth) {
        super(sprite);
        this.health = health;
        this.maxHealth = maxHealth;
    }

    public LivingEntity(Coordinate location, Sprite sprite, boolean visible, int health, int maxHealth) {
        super(location, sprite, visible);
        this.health = health;
        this.maxHealth = maxHealth;
    }

    public LivingEntity(Sprite sprite, boolean visible, int health, int maxHealth) {
        super(sprite, visible);
        this.health = health;
        this.maxHealth = maxHealth;
    }

    /**
     * Restores the health to maximum health. This will work even if {@link #maxHealth} is -1 so use with caution
     */
    public void heal() {
        this.health = maxHealth;
    }

    /**
     * Tried to heal the entity by the given amount of hit points. It it would exceed the maximum HP, it will just be set to the maximum (unless the maximum is -1).
     *
     * @param amount the amount of health to restore
     */
    public void heal(int amount) {
        if (this.health + amount > this.maxHealth && this.maxHealth != -1)
            this.health = this.maxHealth;
        else
            this.health += amount;
    }

    /**
     * Damages the entity by the given amount of hit points
     *
     * @param amount the amount of hit points to remove
     */
    public void damage(int amount) {
        this.health -= amount;
    }

    /**
     * If this entity is dead (health is less than or equal to 0)
     *
     * @return if the entity is dead
     */
    public boolean isDead() {
        return this.health <= 0;
    }

    @Override
    public void render(Graphics2D g, Screen screen) {
        int barWidth = getSprite().getWidth();
        double multiplier = (double) health / (double) maxHealth;
        int filledWidth = (int) (barWidth * multiplier);

        super.render(g, screen);
        g.setColor(new Color(115, 35, 105));
        g.fillRect(getLocation().getX(), getLocation().getY() - 10, barWidth, 5);
        g.setColor(new Color(81, 0, 81));
        g.fillRect(getLocation().getX(), getLocation().getY() - 10, filledWidth, 5);
    }
}
