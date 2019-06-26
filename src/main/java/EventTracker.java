import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class EventTracker implements Tracker {

    private static EventTracker INSTANCE = new EventTracker();

    protected Map<String, Integer> tracker;

    private EventTracker() {
        this.tracker = new HashMap<>();
    }

    synchronized public static EventTracker getInstance() {
        return INSTANCE;
    }

    synchronized public void push(String message) {
        if(tracker.containsKey(message)){
            Integer toReplace = tracker.get(message);
            tracker.replace(message, toReplace+1);
        }
        else{tracker.put(message, 1);}
    }

    synchronized public Boolean has(String message) {
        if(tracker.containsKey(message) && tracker.get(message) > 0){
            return true;
        }


        return false;
    }

    synchronized public void handle(String message, EventHandler e) {
        e.handle();

        if(this.has(message)){
            Integer toReplace = tracker.get(message);
            tracker.replace(message, toReplace-1);}
    }

    // Do not use this. This constructor is for tests only
    // Using it breaks the singleton class
    EventTracker(Map<String, Integer> tracker) {
        this.tracker = tracker;
    }


}
