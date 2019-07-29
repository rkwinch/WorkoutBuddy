package finalproject;

/**
 *
 * An interface representing a Model in the MVC paradigm for the WOTD program.
 * @author Robin Ouzts 
 */
interface Model {
    /**
     * 
     * Starts the model.
     */
    public void start();

    /**
     *
     * Updates the model in response to some flag.
     * Known flags:
     *  0x1 - TOGGLE_PAUSE
     *  0x2 - STOP_MODEL
     * @param flag A flag indicating something to the concrete Model.
     */
    public void update(int flag);
    
    /**
     *
     * Gets data from the model.
     * @return A string representing the Model's current state.
     */
    String getData();
    
    /**
     * Gets a WorkoutDuration from the Model.
     * @return A WorkoutDuration object representing the time remaining
     * on the current Workout.
     */
    WorkoutDuration getDuration();
}
