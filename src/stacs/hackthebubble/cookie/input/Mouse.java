package stacs.hackthebubble.cookie.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;
import stacs.hackthebubble.cookie.input.listener.InternalMouseListener;
import stacs.hackthebubble.cookie.location.Coordinate;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {

    /**
     * Contains all the registered mouse listeners
     */
    private List<InternalMouseListener> listenerList = new ArrayList<>();

    private Coordinate lastKnownPosition;

    public Coordinate getLastKnownPosition() {
        return lastKnownPosition;
    }

    /**
     * Adds a new listener to the mouse system which will be notified of all events. Recommended implementation is though {@link stacs.hackthebubble.cookie.input.listener.AbstractInternalMouseListener}
     *
     * @param listener the listener to register
     */
    public void addListener(InternalMouseListener listener) {
        listenerList.add(listener);
    }

    /**
     * Removes the listener from the system
     *
     * @param listener the listener to remove
     */
    public void removeListener(InternalMouseListener listener) {
        listenerList.remove(listener);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        lastKnownPosition = new Coordinate(e.getX(), e.getY());
        listenerList.forEach(l -> l.mouseClicked(e));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        lastKnownPosition = new Coordinate(e.getX(), e.getY());
        listenerList.forEach(l -> l.mousePressed(e));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        lastKnownPosition = new Coordinate(e.getX(), e.getY());
        listenerList.forEach(l -> l.mouseReleased(e));
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        lastKnownPosition = new Coordinate(e.getX(), e.getY());
        listenerList.forEach(l -> l.mouseEntered(e));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        lastKnownPosition = new Coordinate(e.getX(), e.getY());
        listenerList.forEach(l -> l.mouseExited(e));
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        lastKnownPosition = new Coordinate(e.getX(), e.getY());
        listenerList.forEach(l -> l.mouseDragged(e));
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        lastKnownPosition = new Coordinate(e.getX(), e.getY());
        listenerList.forEach(l -> l.mouseMoved(e));
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        lastKnownPosition = new Coordinate(e.getX(), e.getY());
        listenerList.forEach(l -> l.mouseWheelMoved(e));
    }
}
