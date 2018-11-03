package stacs.hackthebubble.cookie.location;

public class Coordinate {

    /**
     * The x position of this 2d coordinate
     */
    private int x;
    /**
     * The y position of this 2d coordinate
     */
    private int y;

    /**
     * Constructs a new coordinate with the given x and y locations
     * @param x the initial x location
     * @param y the initial y location
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructs a new coordinate at (0, 0)
     */
    public Coordinate() {
        this(0, 0);
    }

    /**
     * Offset this coordinate by the given coordinate and return the result as a new object, making no change to this instance
     * @param coordinate the coordinate by which this coordinate should be offset
     * @return the new coordinate with the x and y locations summed
     */
    public Coordinate offset(Coordinate coordinate) {
        return new Coordinate(coordinate.x + x, coordinate.y + y);
    }

    /**
     * Offset this coordinate by the given amount in x and y and return the result as a new object, making no change to this instance
     * @param x the amount to offset by x
     * @param y the amount to offset by y
     * @return the new coordinate with the x and y locations offset
     */
    public Coordinate offset(int x, int y) {
        return new Coordinate(this.x + x, this.y + y);
    }

    /**
     * Offsets the x coordinate by the given amount. The same as {@code offset(x, 0)}.
     * @param x the amount to offset x by
     * @return the new coordinate with the offset location
     */
    public Coordinate offsetX(int x) {
        return new Coordinate(this.x + x, this.y);
    }
    /**
     * Offsets the y coordinate by the given amount. The same as {@code offset(0, y)}.
     * @param y the amount to offset y by
     * @return the new coordinate with the offset location
     */
    public Coordinate offsetY(int y) {
        return new Coordinate(this.x, this.y + y);
    }

    /**
     * Offsets this coordinate by the given amount. This does not form a new object and will only edit the internal x and y values.
     * @param x
     * @param y
     */
    public void offsetThis(int x, int y) {
        this.x += x;
        this.y += y;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
