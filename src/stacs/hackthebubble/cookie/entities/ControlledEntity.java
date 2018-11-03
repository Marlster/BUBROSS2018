package stacs.hackthebubble.cookie.entities;

import java.io.IOException;
import stacs.hackthebubble.cookie.GameWindow;
import stacs.hackthebubble.cookie.entities.projectile.Projectile;
import stacs.hackthebubble.cookie.events.EventEmitter.EventConstant;
import stacs.hackthebubble.cookie.game.GameState;
import stacs.hackthebubble.cookie.graphics.sprites.Sprite;
import stacs.hackthebubble.cookie.location.Coordinate;

public class ControlledEntity extends LivingEntity {

    private final int JUMP_LENGTH = 1000;
    private long updateSinceLastProjectile = -1;
    private int updatesBetweenProjectiles = 20;

    private double x;
    private double y;

    private double velocityX;
    private double velocityY;

    private int keyJump;
    private int keyLeft;
    private int keyRight;
    private int keyAttack;
    private long jumpTime = -1;

    public ControlledEntity(Coordinate location, Sprite sprite, int health, int maxHealth) {
        super(location, sprite, health, maxHealth);
    }

    public void defineControls(int keyAttack, int keyJump, int keyLeft, int keyRight) {
        this.keyAttack = keyAttack;
        this.keyJump = keyJump;
        this.keyLeft = keyLeft;
        this.keyRight = keyRight;

        x = location.getX();
        y = location.getY();
    }

    @Override
    public void onEvent(String event, Object... data) {
        x += velocityX;
        y += velocityY;
        location = new Coordinate((int) x, (int) y);
        velocityX += (-Math.signum(velocityX)) * 0.5;

        updateSinceLastProjectile++;
        if (!isStatic && jumpTime == -1) {
            if (GameState.getActiveGameState() != null && !isCollidingWithSomething()) {
                if (velocityY < 6)
                    velocityY += 1;
//                velocityY = 3;
            } else {
                velocityY = 0;
            }
        }

        if (jumpTime != -1) {
            if (System.currentTimeMillis() - jumpTime <= JUMP_LENGTH) {
//                location = location.offsetY(-5);
            } else {
                jumpTime = -1;
            }
        }

        if (EventConstant.UPDATE.isEvent(event)) {
            if (GameWindow.getInstance().getKeyboard().isKeyPressed(keyLeft)) {
                if (velocityX > -4)
                    velocityX -= 1;
            }
            if (GameWindow.getInstance().getKeyboard().isKeyPressed(keyRight)) {
                if (velocityX < 4)
                    velocityX += 1;
            }
            if (GameWindow.getInstance().getKeyboard().isKeyPressed(keyAttack) && (updateSinceLastProjectile > updatesBetweenProjectiles || updateSinceLastProjectile == -1)) {
                try {
                    Entity entity = new Projectile(getLocation(), new Sprite("/projectiles/projectile.png"), 7, -3, 0.15f, 0.7f, 1000);
                    updateSinceLastProjectile = 0;
                    GameState.getActiveGameState().addEntities(entity);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (GameWindow.getInstance().getKeyboard().isKeyPressed(keyJump) && jumpTime == -1 && isCollidingWithSomething()) {
                jumpTime = System.currentTimeMillis();
                velocityY = -4.6;
            }
        }
    }

    private boolean isCollidingWithSomething() {
        return GameState.getActiveGameState().getLevel().getBounds().stream().anyMatch(b -> b.isColliding(this, 6));
    }
}
