package finalproject;

import finalproject.GUI.WorkoutOfTheDayGUI;
import finalproject.TUI.TextUserInterface;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * A class representing the Controller in the Model-View-Controller paradigm
 * for the WOTD package.  It is responsible for updating the model and view.
 * @author Robin Ouzts 
 */
public class ProjectController {

    /**
     *
     * A timer used for updating the View periodically.
     */
    private Timer timer;

    /**
     *
     * The instance of the Model the Controller holds.
     */
    Model model = null;

    /**
     *
     * The instance of the View the Controller holds.
     */
    View ui = null;

    /**
     *
     * Getter for the History of a specified user.
     * @param user A string representing the User's name.
     * @return A WorkoutHistory object representing user's history.
     * @throws NoUserHistoryException Thrown when the user has no history.
     * @throws IOException Thrown when the history database cannot be read properly.
     */
    public WorkoutHistory GetHistory(String user) throws NoUserHistoryException, IOException {
        return UserDB.getInstance().QueryUserHistory(user);
    }

    /**
     *
     * Instantiates a model based on the supplied parameters, and scheduled updates
     * for the View.
     * @param durationSelection A string representing the duration the model should run.
     * @param difficultySelection A string representing the difficulty of the model.
     */
    public void setModel(String durationSelection, String difficultySelection) {
        model = new Workout(difficultySelection, durationSelection);
        model.start(); // starts the model
        timer = new Timer();
        TimerTask updateView = new UpdateView(); // timertask for the timer
        timer.schedule(updateView, 0, 100); // updates view every 0.1s
    }

    /**
     *
     * Updates the model with a particular type of information.
     * @param flag A value indicating what type of update this is (e.g. pause, stop)
     */
    public void updateModel(int flag) {
        //can't update model if model doesn't exist
        if (model == null) { // this should never happen
            throw new RuntimeException("Model not instantiated!");
        }
        
        model.update(flag);
    }

    /**
     *
     * Starts the Controller, instantiates and opens the View, and schedules
     * updates for the view.
     * @param options A ProjectOptions object indicating which View to use
     * and if audio should be utilized.
     */
    void start(ProjectOptions options) {
        //instantiates gui
        if ("graphical".equals(options.getDisplayType())) {
            ui = new WorkoutOfTheDayGUI(this, options.getAudioEnabled());
        } else {
        //instantiates text user interface
            ui = new TextUserInterface(this, options.getAudioEnabled());
        }

        ui.open();

        timer = new Timer();
        TimerTask updateView = new UpdateView();
        timer.schedule(updateView, 0, 100); // updates view every 0.1s
    }

    /**
     * 
     * A private inner class used to update the View.  Extends the TimerTask
     * class built into Java.
     */
    private class UpdateView extends TimerTask {

        /**
         *
         * Default constructor - does nothing.
         */
        UpdateView() {
        }

        /**
         * An overridden method from TimerTask that periodically updates the
         * View if the model is available.
         */
        @Override
        public void run() {
            if (model != null && ui != null) {
                ui.update(model.getData(), model.getDuration());
            }
        }
    }
}
