/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject.GUI;

import finalproject.WorkoutHistory;
import finalproject.WorkoutHistoryEntry;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * Concrete Strategy class used to prune and bin workout history data
 * by day of week
 * @author Robin Ouzts 
 */
public class ByWeekdayPruningStrategy implements EntryPrunerStrategy {

    /**
     *
     * Default constructor - does nothing.
     */
    public ByWeekdayPruningStrategy() {
    }

    /**
     * 
     * Given a WorkoutHistory, returns an array of size 7 where each entry
     * in the workout history is binned by day of week.
     * @param history A WorkoutHistory for a user.
     * @return An ArrayList of ints of size 7, where the 0th element represents
     * Sunday, the 1st element represents Monday, and so forth.
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
            Date fileDate;
            try {
                fileDate = new SimpleDateFormat("yyyy-MM-dd").parse(e.getDate());
            } catch (ParseException ex) {
                throw new RuntimeException(ex.getMessage());
            }

            String dayOfWeek = new SimpleDateFormat("E").format(fileDate);
            if ("Sun".equals(dayOfWeek)) {
                dayCounts.set(0, dayCounts.get(0) + 1);
            } else if ("Mon".equals(dayOfWeek)) {
                dayCounts.set(1, dayCounts.get(1) + 1);
            } else if ("Tue".equals(dayOfWeek)) {
                dayCounts.set(2, dayCounts.get(2) + 1);
            } else if ("Wed".equals(dayOfWeek)) {
                dayCounts.set(3, dayCounts.get(3) + 1);
            } else if ("Thu".equals(dayOfWeek)) {
                dayCounts.set(4, dayCounts.get(4) + 1);
            } else if ("Fri".equals(dayOfWeek)) {
                dayCounts.set(5, dayCounts.get(5) + 1);
            } else if ("Sat".equals(dayOfWeek)) {
                dayCounts.set(6, dayCounts.get(6) + 1);
            }
        }

        return dayCounts;
    }

}
