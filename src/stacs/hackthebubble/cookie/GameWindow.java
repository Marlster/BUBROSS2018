package stacs.hackthebubble.cookie;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import stacs.hackthebubble.cookie.events.EventEmitter;
import stacs.hackthebubble.cookie.events.EventEmitter.EventConstant;
import stacs.hackthebubble.cookie.graphics.Screen;

/**
 * The window in which the game is running and controls the launching and execution of the main renderer thread of the program
 */
public class GameWindow extends Canvas implements Runnable {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = (int) ((2 / 3d) * WIDTH);

    /**
     * The frame which contains this canvas and is the base game window
     */
    private JFrame frame;

    /**
     * Thread that contains the base game code in which the main graphics will run (the rendering functions in this class)
     */
    private Thread containerThread;
    /**
     * If the current thread should be running, if set to false it 'should' break the {@link #run()} function
     */
    private boolean running;
    /**
     * The graphics manager for this game. This should hand the actual rendering code to extract it from this class
     */
    private Screen screen;

    /**
     * Constructs a new window, adds this to it and packs it. Prepares the container thread with this runnable but does not launch it
     */
    public GameWindow() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.add(this);
        frame.pack();

        containerThread = new Thread(this, "Game Core Thread");
    }

    /**
     * Via {@link EventQueue#invokeLater(Runnable)}, launches the frame, marks {@link #running} to true and launches the thread
     */
    public void start() {
        EventQueue.invokeLater(() -> {
            frame.setVisible(true);
//            GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);
            screen = new Screen(getWidth(), getHeight());

            running = true;
            containerThread.start();
        });
    }

    /**
     * Controls rendering and updating the game. UPS partially limited to 60UPS (although it is not exact) but FPS is left unlimited.
     * The FPS and UPS will be output each second to console
     */
    @Override
    public void run() {
        final int maxUPS = 60;
        final int maxFPS = 200;

        final int fpsDelta = 1000 / maxFPS;
        final int delta = 1000 / maxUPS;

        long lastUpdate = System.currentTimeMillis();
        long lastRender = System.currentTimeMillis();
        long lastOutput = System.currentTimeMillis();

        int fps = 0;
        int ups = 0;
        while (running) {
            long now = System.currentTimeMillis();

            if (now - lastRender >= fpsDelta) {
                render();
                lastRender = now;
                fps++;
            }

            // Update should only be called 60 times per second so if only 1/60th of a second has passed can we call it
            if (now - lastUpdate >= delta) {
                update();
                ups++;
                lastUpdate = now;
            }

            // Output once per second and reset the fps and the ups count
            if (now - lastOutput >= 1000) {
                frame.setTitle(fps + "fps, " + ups + "ups");

                fps = 0;
                ups = 0;

                lastOutput = now;
            }
        }
    }

    /**
     * Sets up canvas as double buffered on first run and will then hand off rendering of the actual game their respective handlers
     */
    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }// Render single frame
        do {
            // The following loop ensures that the contents of the drawing buffer
            // are consistent in case the underlying surface was recreated
            do {
                // Get a new graphics context every time through the loop
                // to make sure the strategy is validated
                Graphics2D g = (Graphics2D) bs.getDrawGraphics();
                screen.render(g);
                // Dispose the graphics
                g.dispose();

                // Repeat the rendering if the drawing buffer contents
                // were restored
            } while (bs.contentsRestored());

            // Display the buffer
            bs.show();

            // Repeat the rendering if the drawing buffer was lost
        } while (bs.contentsLost());
    }

    /**
     * Called approximately 60 times per second and should be used for fixed rate updates
     */
    private void update() {
        EventEmitter.emit(EventConstant.UPDATE);
    }

}
