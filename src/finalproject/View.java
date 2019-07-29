package finalproject;

/**
 *
 * An interface representing a View in the Model-View-Controller paradigm.
 * @author Robin Ouzts 
 */
public interface View {

    /**
     *
     * Opens the view for display.
     */
    public void open();

    /**
     *
     * Updates the View with data and a duration.
     * @param data The data to use to update the View.
     * @param duration The duration to use to update the View.
     */
    public void update(String data, WorkoutDuration duration);
}
