package kz.kbtu.patterns;

import java.util.ArrayList;
import java.util.List;

public class EventManager {
    private static EventManager instance;
    private final List<Observer> observers = new ArrayList<>();
    private final List<String> log = new ArrayList<>();

    private EventManager() {}

    public static synchronized EventManager getInstance() {
        if (instance == null) instance = new EventManager();
        return instance;
    }

    public void subscribe(Observer o) { observers.add(o); }

    public void unsubscribe(Observer o) { observers.remove(o); }

    public void notify(String message) {
        log.add(message);
        for (Observer o : observers) o.update(message);
    }

    public List<String> getLog() { return log; }

    public void clear() {
        observers.clear();
        log.clear();
    }
}
