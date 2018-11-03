package stacs.hackthebubble.cookie.events;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A basic system for sending events throughout the program using a basic subscription system
 */
public class EventEmitter {

    /**
     * Contains all event types and their lists of associated listeners. Due to the multithreaded nature, it uses a concurrent implementation and should be
     * synchronised at all times of use
     */
    private static final ConcurrentHashMap<String, List<Listener>> subscribedListeners = new ConcurrentHashMap<>();

    /**
     * Adds the given listener to the given event. Once this executes fully it should receive all relevant {@link #emit(String, Object...)} calls
     * via the {@link Listener#onEvent(String, Object...)} calls.
     *
     * @param event    the name of the event to bind this to
     * @param listener the listener to use
     */
    public static void subscribe(String event, Listener listener) {
        synchronized (subscribedListeners) {
            if (subscribedListeners.containsKey(event)) {
                subscribedListeners.get(event).add(listener);
            } else {
                List<Listener> listeners = new ArrayList<>();
                listeners.add(listener);
                subscribedListeners.put(event, listeners);
            }
        }
    }

    /**
     * Removes the given listener from the given event. It should not longer receive updates once this has been called and completed.
     *
     * @param event    the name of the event this listener is subscribed to
     * @param listener the listener instance
     */
    public static void unsubscribe(String event, Listener listener) {
        synchronized (subscribedListeners) {
            if (subscribedListeners.containsKey(event) && subscribedListeners.get(event).contains(listener)) {
                subscribedListeners.get(event).remove(listener);
            }
        }
    }

    /**
     * Emits the given event and data sets to the listeners that have been registered to this event via {@link #subscribe(String, Listener)}.
     *
     * @param event the name of the event to emit
     * @param data  data to be sent with the request
     */
    public static void emit(String event, Object... data) {
        synchronized (subscribedListeners) {
            if (subscribedListeners.containsKey(event)) {
                subscribedListeners.get(event).forEach(e -> e.onEvent(event, data));
            }
        }
    }

}
