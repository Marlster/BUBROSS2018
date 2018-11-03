package stacs.hackthebubble.cookie.entities.nonphysical;

import stacs.hackthebubble.cookie.entities.Entity;
import stacs.hackthebubble.cookie.location.Coordinate;

public class Bounds {

    private Coordinate topLeft;
    private Coordinate bottomRight;

    public Bounds(Coordinate topLeft, Coordinate topRight) {
        this.topLeft = topLeft;
        this.bottomRight = topRight;
    }

    public boolean isColliding(Coordinate point) {
        return point.getX() > topLeft.getX() && point.getX() < bottomRight.getX() && point.getY() > topLeft.getY() && point.getY() < bottomRight.getY();
    }

    public boolean isColliding(Entity entity, int yOffset) {
        // Testing points:
        // * -- * -- *
        // |         |
        // *         *
        // |         |
        // * -- * -- *
        // 8 points
        Coordinate topLeft = entity.getLocation();
        Coordinate topMiddle = entity.getLocation().offset(entity.getSprite().getWidth() / 2, 0);
        Coordinate topRight = entity.getLocation().offset(entity.getSprite().getWidth(), 0);

        Coordinate middleLeft = entity.getLocation().offset(0, entity.getSprite().getHeight() / 2);
        Coordinate middleRight = entity.getLocation().offset(entity.getSprite().getWidth(), entity.getSprite().getHeight() / 2);

        Coordinate bottomLeft = entity.getLocation().offset(0, entity.getSprite().getHeight());
        Coordinate bottomMiddle = entity.getLocation().offset(entity.getSprite().getWidth() / 2, entity.getSprite().getHeight());
        Coordinate bottomRight = entity.getLocation().offset(entity.getSprite().getWidth(), entity.getSprite().getHeight());

        return isColliding(topLeft.offsetY(yOffset)) ||
            isColliding(topMiddle.offsetY(yOffset)) ||
            isColliding(topRight.offsetY(yOffset)) ||
            isColliding(middleLeft.offsetY(yOffset)) ||
            isColliding(middleRight.offsetY(yOffset)) ||
            isColliding(bottomLeft.offsetY(yOffset)) ||
            isColliding(bottomMiddle.offsetY(yOffset)) ||
            isColliding(bottomRight.offsetY(yOffset));
    }

    public boolean isColliding(Entity entity) {
        return isColliding(entity, 0);
    }

    public Coordinate getBottomRight() {
        return bottomRight;
    }

    public Coordinate getTopLeft() {
        return topLeft;
    }
}
