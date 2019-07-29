package finalproject;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The CancelEvent class is an extension of Java's
 * TimerTask.  Its purpose is to run after a specified delay,
 * and then cancel the timer that spun it off along with
 * any other tasks that timer is responsible for.
 * @author Robin Ouzts 
 */
public class CancelEvent extends TimerTask {

    /**
     *
     * A reference to the Timer that scheduled this task.
     * Needed in order to cancel it at the execution of this class.
     */
    Timer event;

    /**
     *
     * The Workout that should be stopped upon execution of this
     * TimerTask.
     */
    Workout workout;

    /**
     *
     * @param workout The Workout that should be stopped upon execution of this.
     * @param event The Timer of the parent that will be cancelled.
     */
    public CancelEvent(Workout workout, Timer event) {
        this.workout = workout;
        this.event = event;
    }

    /**
     * 
     * Overridden method, automatically called when this TimerTask's
     * scheduled execution time arrives.  The overriden version stops
     * the workout and cancels all other TimerTasks associated with the
     * Workout's timer.
     */
    @Override
    public void run() {
        //when this runs, this cancels the workout
        workout.stop();
        event.cancel();

    }

}
