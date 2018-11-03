package stacs.hackthebubble.cookie.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

public class Keyboard implements KeyListener {

    /**
     * Contains all the keys currently pressed to allow for multiple key presses being detected
     */
    private Set<Integer> keysPressed = new HashSet<>();

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keysPressed.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keysPressed.remove(e.getKeyCode());
    }

    /**
     * Returns if the current key is pressed, this should be a key code from {@link KeyEvent}
     *
     * @param key the key code
     * @return if that key is pressed
     */
    public boolean isKeyPressed(int key) {
        return keysPressed.contains(key);
    }
}
