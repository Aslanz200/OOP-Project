package kz.kbtu.patterns;

/** Observer pattern — subscribers receive event notifications from {@link EventManager}. */
public interface Observer {
    void update(String message);
}
