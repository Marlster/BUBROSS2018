package stacs.hackthebubble.cookie.entities;

import stacs.hackthebubble.cookie.GameWindow;
import stacs.hackthebubble.cookie.events.EventEmitter.EventConstant;
import stacs.hackthebubble.cookie.game.GameState;
import stacs.hackthebubble.cookie.graphics.sprites.Sprite;
import stacs.hackthebubble.cookie.location.Coordinate;

public class ControlledEntity extends LivingEntity {

    private final int JUMP_LENGTH = 1000;

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
    }

    @Override
    public void onEvent(String event, Object... data) {
        if (!isStatic && jumpTime == -1) {
            if (GameState.getActiveGameState() != null && !isCollidingWithSomething()) {
                location = location.offsetY(5);
            }
        }

        if (jumpTime != -1) {
            if (System.currentTimeMillis() - jumpTime <= JUMP_LENGTH) {
                location = location.offsetY(-5);
            } else {
                jumpTime = -1;
            }
        }

        if (EventConstant.UPDATE.isEvent(event)) {
            if (GameWindow.getInstance().getKeyboard().isKeyPressed(keyLeft)) {
                location = location.offsetX(-5);
            }
            if (GameWindow.getInstance().getKeyboard().isKeyPressed(keyRight)) {
                location = location.offsetX(5);
            }
            if (GameWindow.getInstance().getKeyboard().isKeyPressed(keyJump) && jumpTime == -1 && isCollidingWithSomething()) {
                jumpTime = System.currentTimeMillis();
            }
        }
    }

    private boolean isCollidingWithSomething() {
        return GameState.getActiveGameState().getLevel().getBounds().stream().anyMatch(b -> b.isColliding(this, 6));
    }
}
