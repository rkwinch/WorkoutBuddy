package finalproject;

import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

/**
 *
 * A class representing a single exercise chosen out of a list
 * of possible moves.  This extends TimerTask and is automatically
 * executed periodically.
 * @author Robin Ouzts 
 */
public class ExerciseMove extends TimerTask {

    /**
     *
     * The duration of this ExerciseMove.
     */
    long duration;

    /**
     *
     * A list of all possible moves this ExerciseMove could ever assume.
     */
    ArrayList<String> moves;

    /**
     *
     * The name of this ExerciseMove.
     */
    String name;
    
    /**
     *
     * The number of possible exercises this ExerciseMove could ever assume.
     */
    int numbOfExercises;
    
    /**
     * 
     * A reference to the Workout that scheduled this ExerciseMove.
     */
    Workout parent;

    /**
     *
     * @param parent A reference to the Workout that scheduled this ExerciseMove.
     * @param name The name of the workout.
     * @param moveset The list of possible moves
     * @param totalDuration The (max) duration of this exercise.
     */
    ExerciseMove(Workout parent, String name, ArrayList<String> moveset, long totalDuration) {
        this.parent = parent;
        this.name = name;
        moves = new ArrayList<>(moveset); // making a shallow copy of moveset for moves
        numbOfExercises = moves.size(); // gets length of moves for total number of moves
        this.duration = totalDuration;
    }

    /**
     * 
     * Overridden method from TimerTask - executed automatically upon reaching
     * its scheduled execution time.
     * Simply chooses a random move from the list of possible moves, and stores
     * it in the Workout associated with this ExerciseMove.
     */
    @Override
    public void run() {
        Random randNumb = new Random();
        int randomInt = randNumb.nextInt(numbOfExercises); // getting random # based on number of exercise moves
        parent.setCurrentWorkout(moves.get(randomInt));
    }

    /**
     *
     * Getter for this ExerciseMove's name.
     * @return The name of this exercise move.
     */
    String getName() {
        return name;
    }
}
