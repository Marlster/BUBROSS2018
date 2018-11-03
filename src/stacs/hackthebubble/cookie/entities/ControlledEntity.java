package stacs.hackthebubble.cookie.entities;

import stacs.hackthebubble.cookie.GameWindow;
import stacs.hackthebubble.cookie.events.EventEmitter.EventConstant;
import stacs.hackthebubble.cookie.graphics.sprites.Sprite;
import stacs.hackthebubble.cookie.location.Coordinate;

public class ControlledEntity extends LivingEntity {

    private int keyJump;
    private int keyLeft;
    private int keyRight;
    private int keyAttack;

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
        super.onEvent(event, data);
        if (EventConstant.UPDATE.isEvent(event)) {
            if (GameWindow.getInstance().getKeyboard().isKeyPressed(keyLeft)) {
                location = location.offsetX(-5);
            }
            if (GameWindow.getInstance().getKeyboard().isKeyPressed(keyRight)) {
                location = location.offsetX(5);
            }
        }
    }
}
