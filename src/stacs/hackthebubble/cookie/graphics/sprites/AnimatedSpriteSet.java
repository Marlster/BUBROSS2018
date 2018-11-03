package stacs.hackthebubble.cookie.graphics.sprites;

import java.awt.image.BufferedImage;
import stacs.hackthebubble.cookie.events.EventEmitter.EventConstant;
import stacs.hackthebubble.cookie.events.Listener;

public class AnimatedSpriteSet extends Sprite implements Listener {

    /**
     * The number of updates since the last frame change
     */
    private int updateCount;
    /**
     * The number of frames that need to pass before the frame changes
     */
    private int updatesPerFrameChange;
    /**
     * The current frame that the animation is on. Must be a valid index in {@link #imageSet}
     */
    private int frameIndex = 0;
    /**
     * The set of image frames that compose this animation
     */
    private BufferedImage[] imageSet;

    /**
     * Constructs an animation using the provided frames that will change with the given amount of updates. This will not subscribe the update
     * event itself in case the user of the animation is aready registered at which point the events should be passed rather than having another
     * listener subscribed. It starts on frame 0
     *
     * @param images                the set of frames
     * @param updatesPerFrameChange the amount of frames that need to pass until the frame changes
     */
    public AnimatedSpriteSet(BufferedImage[] images, int updatesPerFrameChange) {
        super(images[0]);
        this.updatesPerFrameChange = updatesPerFrameChange;
        this.imageSet = images;
    }

    @Override
    public int getWidth() {
        return imageSet[frameIndex].getWidth();
    }

    @Override
    public int getHeight() {
        return imageSet[frameIndex].getHeight();
    }

    /**
     * Get the currently active image in this animation
     *
     * @return the current animation frame
     */
    @Override
    public BufferedImage getImage() {
        return imageSet[frameIndex];
    }

    /**
     * If it is an update, it will determine if the frame needs changing automatically. See parent for param details
     */
    @Override
    public void onEvent(String event, Object... data) {
        if (EventConstant.UPDATE.isEvent(event)) {
            if (++updateCount >= updatesPerFrameChange) {
                changeFrame();
                updateCount = 0;
            }
        }
    }

    /**
     * Increments the frame index and will wrap it back to 0 if it exceeds the size of the internal image array
     */
    private void changeFrame() {
        if (++frameIndex >= imageSet.length)
            frameIndex = 0;
    }
}
