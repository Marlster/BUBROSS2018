package stacs.hackthebubble.cookie;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

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
        final int delta = 1000 / maxUPS;
        long lastUpdate = System.currentTimeMillis();
        long lastOutput = System.currentTimeMillis();

        int fps = 0;
        int ups = 0;
        while (running) {
            render();
            fps++;

            // Update should only be called 60 times per second so if only 1/60th of a second has passed can we call it
            if (System.currentTimeMillis() - lastUpdate >= delta) {
                update();
                ups++;
                lastUpdate = System.currentTimeMillis();
            }

            // Output once per second and reset the fps and the ups count
            if (System.currentTimeMillis() - lastOutput >= 1000) {
                System.out.println(fps + "fps, " + ups + "ups");

                fps = 0;
                ups = 0;

                lastOutput = System.currentTimeMillis();
            }
        }
    }

    /**
     * Sets up canvas as double buffered on first run and will then hand off rendering of the actual game their respective handlers
     */
    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            return;
        }

        Graphics2D g = (Graphics2D) bs.getDrawGraphics();

        // TODO hand off graphics to the external renderers

        g.dispose();
        bs.show();
    }

    /**
     * Called approximately 60 times per second and should be used for fixed rate updates
     */
    private void update() {

    }

}
