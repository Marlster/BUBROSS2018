package stacs.hackthebubble.cookie.events;

public interface Listener {

    void onEvent(String event, Object... data);

}
