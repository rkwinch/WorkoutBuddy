package finalproject;

import java.util.ArrayList;
import java.util.Timer;

/**
 *
 * A concrete instantiation of the Model interface.  Represents
 * a Workout routine.
 * @author Robin Ouzts 
 */
public class Workout implements Model {

    /**
     *
     * The duration of the workout.
     */
    private final long duration;
    
    /**
     *
     * An event to cancel the workout after a specified time.
     */
    private CancelEvent endWorkout = null;

    /**
     *
     * A representation of periodic cardio-based exercises.
     */
    private ExerciseMove exerciseCardio = null;
    
    /**
     *
     * A representation of periodic lower-body-based exercises.
     */
    private ExerciseMove exerciseLower = null;
    
    /**
     *
     * A representation of periodic upper-body-based exercises.
     */
    private ExerciseMove exerciseUpper = null;

    /**
     *
     * Helper data for the duration remaining when the model received
     * a pause event.
     */
    private long pausedDuration = 0;

    /**
     *
     * A shallow copy of the task being executed when paused.
     */
    private ExerciseMove pausedTask = null;

    /**
     *
     * The period the ExerciseMoves should recur at.
     */
    private final long period;

    /**
     *
     * A representation of the resting event at the end of 3 exercises.
     */
    private ExerciseMove rest = null;

    /**
     *
     * A flag indicating if the model is stopped or not.
     */
    private boolean stopped = false;

    /**
     *
     * The remaining duration on the overall exercise routine.
     */
    private long totalRemainingDuration = 0;

    /**
     *
     * A list of possible cardio exercises.
     */
    protected ArrayList<String> cardio;

    /**
     *
     * The currently executing exercise.
     */
    protected String currentExercise;

    /**
     *
     * A list of possible lower body exercises.
     */
    protected ArrayList<String> lower;

    /**
     *
     * The duration remaining on the current exercise.
     */
    protected long remainingDuration;

    /**
     *
     * A list of possible rest moves.
     */
    protected ArrayList<String> restMoves;

    /**
     *
     * A Timer instance used to schedule the ExerciseMoves.
     */
    protected Timer timer;

    /**
     *
     * A list of possible upper body moves.
     */
    protected ArrayList<String> upper;

    /**
     *
     * The duration the workout is intended to last.
     */
    protected int workoutDuration;

    /**
     *
     * A flag indicating if the model is paused.
     */
    Boolean paused = false;

    /**
     *
     * Constructs a Workout of given difficulty and duration.
     * @param difficulty A string, either "beginner" or "advanced" representing
     * the difficulty of the workout.
     * @param duration A string, either "15 min" or "30 min" that represents
     * the duration of the workout.
     */
    public Workout(String difficulty, String duration) {
        this.duration = 45000; // 45000 ms = 45 s
        period = this.duration * 4; // period is 4 durations
        if (duration.equals("15 min")) {
            workoutDuration = 15; //15 min
        } else {
            workoutDuration = 30; // 30 min
        }

        upper = new ArrayList<>();
        cardio = new ArrayList<>();
        lower = new ArrayList<>();
        restMoves = new ArrayList<>();
        if ("Beginner".equals(difficulty)) {
            upper.add("PUSH-UPS");
            upper.add("PLANKS");
            lower.add("LUNGES");
            lower.add("SQUATS");
            cardio.add("SIT-UPS");
            cardio.add("JUMPING JACKS");
            restMoves.add("REST");
        } else {
            upper.add("WIDE GRIP PUSH-UPS");
            upper.add("CLOSE GRIP PUSH-UPS");//
            upper.add("PLANKS");//
            upper.add("HAND RELEASE PUSH-UPS");//
            cardio.add("SIT-UPS");//
            cardio.add("JUMPING JACKS");
            cardio.add("HIGH KNEES");//
            cardio.add("BURPEES");//
            lower.add("SQUATS");
            lower.add("LUNGES");
            lower.add("SQUAT JUMPS");//
            lower.add("BEAR CRAWLS");//
            restMoves.add("REST");//
        }
    }

    /**
     * Getter for the current workout.
     * @return The current exercise being performed.
     */
    public String getCurrentWorkout() {
        return currentExercise;
    }

    /**
     * Setter for the current workout.
     * @param get The workout that should be executed.
     */
    public void setCurrentWorkout(String get) {
        currentExercise = get;
    }

    /**
     *
     * Overrides the Model interface method and returns data - in this case,
     * the current exercise being executed, or "STOPPED" if the model is stopped.
     * @return A string representing the current exercise, or "STOPPED" if the model
     * is stopped.  Returns empty string if there iks no exercise and the model
     * is not stopped.
     */
    @Override
    public String getData() {
        if (currentExercise == null) {
            return "";
        }
        if (stopped) { // End of entire run
            return "STOPPED";
        }
        return currentExercise;
    }

    /**
     *
     * Gets the remaining duration as a WorkoutDuration object.
     * @return A WorkoutDuration object that represents the time left on
     * the current exercise and the overall workout.
     */
    @Override
    public WorkoutDuration getDuration() {
        if (paused) {
            return new WorkoutDuration(pausedDuration / 1000.0, totalRemainingDuration);
        }

        if (currentExercise == null || stopped == true) {
            return new WorkoutDuration(0, 0);
        }

        ExerciseMove task = GetCurrentlyExecutingTimerTask();

        long dur = period - (System.currentTimeMillis() - getNextTask(task).scheduledExecutionTime());

        while (dur < 0) {
            task = GetCurrentlyExecutingTimerTask();
            dur = period - (System.currentTimeMillis() - getNextTask(task).scheduledExecutionTime());
        }
        return new WorkoutDuration(dur / 1000.0, GetTotalRemainingDuration());
    }

    /**
     *
     * Begins a workout by scheduling its ExerciseMoves.
     */
    public void playWorkout() {
        timer = new Timer();
        InitializeExercises();
        //making timers for each exercises and to end the workout
        //so, make first exercise with no delay, then do the rest of them
        //with the delay of the duration for each exercise, which we determined to be 45s each
        //the period is how frequently this will occur.  This should be 4 * duration to
        //create repeating sets of upper, lower, and cardio exercises with rests
        timer.schedule(exerciseUpper, 0, period);
        timer.schedule(exerciseLower, duration, period);
        timer.schedule(exerciseCardio, 2 * duration, period);
        timer.schedule(rest, 3 * duration, period);
        //to actually end the workout
        timer.schedule(endWorkout, workoutDuration * 60 * 1000);
    }

    /**
     *
     * Overrides the Model interface's start method, and starts the workout.
     */
    @Override
    public void start() {
        playWorkout();
    }

    /**
     * Overrides the Model interface's update method, and updates the model.
     * In this concrete Model, only the flags TOGGLE_PAUSE and STOP_WORKOUT
     * have meaning.
     * @param flag The flag to update the model with
    */
    @Override
    public void update(int flag) {
        switch (flag) {
            case Utilities.TOGGLE_PAUSE:
                if (!paused) {
                    pausedTask = GetCurrentlyExecutingTimerTask();
                    pausedDuration = (long) (getDuration().getExerciseDuration() * 1000.0);
                    totalRemainingDuration = GetTotalRemainingDuration();
                    CancelExercises();
                } else { // unpausing
                    InitializeExercises();
                    timer.schedule(getNextTask(pausedTask), pausedDuration, period);
                    timer.schedule(getNextTask(getNextTask(pausedTask)), pausedDuration + duration, period);
                    timer.schedule(getNextTask(getNextTask(getNextTask(pausedTask))), pausedDuration + 2 * duration, period);
                    timer.schedule(getNextTask(getNextTask(getNextTask(getNextTask(pausedTask)))), pausedDuration + 3 * duration, period);
                    timer.schedule(endWorkout, totalRemainingDuration);
                }
                paused = !paused;
                break;
            case Utilities.STOP_WORKOUT:
                CancelExercises();
            default:
                break;
        }

    }

    /**
     *
     * Cancels all ExerciseMoves associated with this Workout.
     */
    private void CancelExercises() {
        exerciseUpper.cancel();
        exerciseLower.cancel();
        exerciseCardio.cancel();
        rest.cancel();
        endWorkout.cancel();
    }

    /**
     *
     * Gets the ExerciseMove that is currently being executed.
     * @return A shallow copy of the currently executing ExcersizeMove.
     */
    private ExerciseMove GetCurrentlyExecutingTimerTask() {
        if (upper.contains(currentExercise) && exerciseUpper != null) {
            return exerciseUpper;
        } else if (cardio.contains(currentExercise) && exerciseCardio != null) {
            return exerciseCardio;
        } else if (lower.contains(currentExercise) && exerciseLower != null) {
            return exerciseLower;
        } else if (restMoves.contains(currentExercise) && rest != null) {
            return rest;
        }
        return null;
    }

    /**
     *
     * Gets the duration remaining on the overall workout.
     * @return The duration remaining on the overall workout.
     */
    private long GetTotalRemainingDuration() {
        return endWorkout.scheduledExecutionTime() - System.currentTimeMillis();
    }

    /**
     *
     * Initializes or re-initializes the ExerciseMoves contained in this class.
     */
    private void InitializeExercises() {
        exerciseUpper = new ExerciseMove(this, "Upper", upper, duration);
        exerciseLower = new ExerciseMove(this, "Lower", lower, duration);
        exerciseCardio = new ExerciseMove(this, "Cardio", cardio, duration);
        rest = new ExerciseMove(this, "Rest", restMoves, duration);
        endWorkout = new CancelEvent(this, timer);
    }

    /**
     * Given a particular type of ExerciseMove, gets the one that should
     * follow it.
     * @param task One of the ExerciseMoves used in Workout.
     * @return The next ExerciseMove that will execute.
     */
    private ExerciseMove getNextTask(ExerciseMove task) {
        switch (task.getName()) {
            case "Upper":
                return exerciseLower;
            case "Lower":
                return exerciseCardio;
            case "Cardio":
                return rest;
            default:
                return exerciseUpper;
        }
    }

    /**
     * Given a string, returns the corresponding ExerciseMove with the
     * name matching that string.
     * @param name The name to query for.
     * @return The ExerciseMove corresponding to name.
     */
    private ExerciseMove getTask(String name) {
        switch (name) {
            case "Upper":
                return exerciseUpper;
            case "Lower":
                return exerciseLower;
            case "Cardio":
                return exerciseCardio;
            default:
                return rest;
        }
    }

    /**
     *
     * Stops a workout by canceling all recurring tasks.
     */
    void stop() {
        stopped = true;
        paused = false;
        timer.cancel();
        timer.purge();
        InitializeExercises();
    }

}
