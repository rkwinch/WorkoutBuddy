package finalproject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * A class representing a user's Workout History.
 * @author Robin Ouzts 
 */
public class WorkoutHistory {
    /**
     *
     * An array of strings; each representing an individual workout
     * comprising this history.
     */
    private String[] hist;

    /**
     *
     * The username this history is associated with.
     */
    private String username;

    /**
     *
     * Constructs a WorkoutHistory from a list of data and a username.
     * @param username The user this data is associated with.
     * @param history An array of workouts; each '/' separated between
     * date, duration, and difficulty.
     */
    public WorkoutHistory(String username, String[] history) {
        this.username = username;
        hist = history.clone();
    }


    /**
     *
     * Returns all WorkoutHistoryEntries in this WorkoutHistory in an iterable
     * format.
     * @return A means of iterating over the individual workouts comprising
     * the user's history.
     */
    public Iterable<WorkoutHistoryEntry> getEntries() {
        ArrayList<WorkoutHistoryEntry> list = new ArrayList<WorkoutHistoryEntry>();

        for (int i = 0; i < hist.length; i++) {
            String s = hist[i];
            String[] delineated = s.split("/");
            WorkoutHistoryEntry entry = new WorkoutHistoryEntry(delineated);
            list.add(entry);
        }

        return list;
    }
    
    /**
     *
     * Returns a textual representing of getEntries
     * @return A means of iterating over the user's workout history
     * as strings.
     */
    public Iterable<String> getText() {
        return Arrays.asList(hist);
    }

    /**
     *
     * Getter for the History's username.
     * @return The username associated with this WorkoutHistory.
     */
    public String getUsername() {
        return username;
    }
}
