package finalproject;

/**
 *
 * An individual entry in a user's WorkoutHistory.
 * @author Robin Ouzts 
 */
public class WorkoutHistoryEntry {
    /**
     *
     * The date of the workout.
     */
    private String date;
    /**
     *
     * The difficult of the workout.
     */
    private String difficulty;

    /**
     *
     * The duration of the workout.
     */
    private String duration;

    /**
     *
     * Constructs a workout history entry from a 3-tuple of data.
     * @param delineated An array of size 3 representing the date, difficulty,
     * and duration of a workout.
     * @throws RuntimeException if the length of delineated is not 3.
     */
    public WorkoutHistoryEntry(String[] delineated) {
        if (delineated.length != 3) {
            throw new RuntimeException("WorkoutHistoryEntry could not be constructed - incorrect format");
        } else {
            date = delineated[0];
            difficulty = delineated[1];
            duration = delineated[2];
        }
    }

    /**
     *
     * Getter for the date of this entry.
     * @return The date this workout was performed.
     */
    public String getDate() {
        return date;
    }

    /**
     *
     * Getter for the difficulty of this entry.
     * @return The difficulty of this workout.
     */
    public String getDifficulty() {
        return difficulty;
    }

    /**
     *
     * Getter for the duration of this entry.
     * @return The duration of this workout.
     */
    public String getDuration() {
        return duration;
    }

}
