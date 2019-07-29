package finalproject;

/**
 *
 * A simple wrapper class around an exercise and workout duration.
 * @author Robin Ouzts 
 */
public class WorkoutDuration {

    /**
     *
     * The time remaining on the current exercise.
     */
    private double exerciseDuration;

    /**
     *
     * The time remaining on the overall workout.
     */
    private double workoutDuration;

    /**
     *
     * Constructs a WorkoutDuration from an exercise and workout
     * duration.
     * @param exerciseDur Time left on the exercise.
     * @param workoutDur Time left on the workout.
     */
    public WorkoutDuration(double exerciseDur, double workoutDur) {
        this.exerciseDuration = exerciseDur;
        this.workoutDuration = workoutDur;
    }

    /**
     * Getter for exerciseDuration.
     * @return The time left on the exercise.
     */
    public double getExerciseDuration() {
        return exerciseDuration;
    }
    
    /**
     *
     * Getter for workoutDuration.
     * @return The time left on the workout.
     */
    public double getWorkoutDuration() {
        return workoutDuration;
    }
}
