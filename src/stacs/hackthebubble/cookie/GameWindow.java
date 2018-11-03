package stacs.hackthebubble.cookie;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class GameWindow extends Canvas implements Runnable {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = (int) ((2 / 3d) * WIDTH);

    private JFrame frame;
    private BufferedImage render;

    private Thread containerThread;
    private boolean running;

    public GameWindow() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.add(this);
        frame.pack();

        containerThread = new Thread(this, "Game Core Thread");
    }

    public void start() {
        EventQueue.invokeLater(() -> {
            frame.setVisible(true);
            running = true;
            containerThread.start();
        });
    }

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
            if (System.currentTimeMillis() - lastUpdate >= delta) {
                update();
                ups++;
                lastUpdate = System.currentTimeMillis();
            }

            if (System.currentTimeMillis() - lastOutput >= 1000) {
                System.out.println(fps + "fps, " + ups + "ups");
            }
        }
    }

    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            return;
        }

        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        g.setColor(Color.MAGENTA);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.dispose();
        bs.show();
    }

    private void update() {

    }

}
