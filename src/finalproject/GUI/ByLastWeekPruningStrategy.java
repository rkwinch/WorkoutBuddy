package finalproject.GUI;

import finalproject.WorkoutHistory;
import finalproject.WorkoutHistoryEntry;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 * Concrete Strategy class used to prune and bin workout history data
 * from the last week.
 * @author Robin Ouzts 
 */
public class ByLastWeekPruningStrategy implements EntryPrunerStrategy {

    /**
     *
     * Default constructor - does nothing.
     */
    public ByLastWeekPruningStrategy() {
    }

    /**
     * 
     * Given a WorkoutHistory, returns an array of size 7 where each entry
     * in the workout history is binned by the most recent day of the week.
     * @param history A WorkoutHistory for a user.
     * @return An ArrayList of ints of size 7, where the 0th element represents
     * today, the 1st element represents yesterday, and so forth.
     */
    @Override
    public ArrayList<Integer> executeStrategy(WorkoutHistory history) {
        ArrayList<Integer> dayCounts = new ArrayList<Integer>();
        for (int i = 0; i < 7; i++) {
            dayCounts.add(0);
        }
        if (history == null) {
            return dayCounts;
        }
        for (WorkoutHistoryEntry e : history.getEntries()) {
            Date date = new Date();
            Date fileDate;
            try {
                fileDate = new SimpleDateFormat("yyyy-MM-dd").parse(e.getDate());
            } catch (ParseException ex) {
                throw new RuntimeException(ex.getMessage());
            }

            long millisDiff = Math.abs(date.getTime() - fileDate.getTime());
            int deltaDays = (int) TimeUnit.DAYS.convert(millisDiff, TimeUnit.MILLISECONDS);
            if (deltaDays > 6) {
                continue;
            }
            dayCounts.set(deltaDays, dayCounts.get(deltaDays) + 1);
        }

        return dayCounts;
    }

}
