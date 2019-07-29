package finalproject.GUI;

import finalproject.WorkoutHistory;
import java.util.ArrayList;

/**
 *
 * An interface used to enforce the Strategy design pattern for
 * pruning workout history entries a certain way.
 * @author Robin Ouzts 
 */
public interface EntryPrunerStrategy {

    /**
     *
     * Executes the strategy.
     * @param history A history of workouts.
     * @return A pruned and binned history list.
     */
    public ArrayList<Integer> executeStrategy(WorkoutHistory history);
}
