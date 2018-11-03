package stacs.hackthebubble.cookie.entities.projectile;

import stacs.hackthebubble.cookie.entities.Entity;
import stacs.hackthebubble.cookie.graphics.sprites.Sprite;
import stacs.hackthebubble.cookie.location.Coordinate;

public class Projectile extends Entity {

    private float velocityX;
    private float velocityY;
    private float dropOff;
    private float slowDown;
    private int distance;
    private int distanceTravelled;

    private double x;
    private double y;

    public Projectile(Coordinate location, Sprite sprite, float velocityX, float velocityY, float dropOff, float slowDown, int distance) {
        super(location, sprite);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.dropOff = dropOff;
        this.slowDown = slowDown;
        this.distance = distance;

        x = location.getX();
        y = location.getY();

        freeze();
    }

    @Override
    public void onEvent(String event, Object... data) {
        super.onEvent(event, data);

        if (velocityX <= 0 || distanceTravelled > distance) {
            hide();
            delete();
        }

        distanceTravelled += velocityX;
        x += velocityX;
        y += velocityY;

        location = new Coordinate((int) Math.round(x), (int) Math.round(y));

        velocityX += slowDown;
        velocityY += dropOff;
    }
}
