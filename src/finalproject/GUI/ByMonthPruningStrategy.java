/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject.GUI;

import finalproject.WorkoutHistory;
import finalproject.WorkoutHistoryEntry;
import java.util.ArrayList;

/**
 *
 * Concrete Strategy class used to prune and bin workout history data
 * by month
 * @author Robin Ouzts 
 */
public class ByMonthPruningStrategy implements EntryPrunerStrategy {

    /**
     *
     * Default constructor - does nothing.
     */
    public ByMonthPruningStrategy() {
    }

    /**
     * 
     * Given a WorkoutHistory, returns an array of size 7 where each entry
     * in the workout history is binned by month.
     * @param history A WorkoutHistory for a user.
     * @return An ArrayList of ints of size 12, where the 0th element represents
     * January, the 1st element represents February, and so forth.
     */
    public ArrayList<Integer> executeStrategy(WorkoutHistory history) {
        ArrayList<Integer> monthCounts = new ArrayList<Integer>();
        for (int i = 0; i < 12; i++) {
            monthCounts.add(0);
        }
        if (history == null) {
            return monthCounts;
        }

        for (WorkoutHistoryEntry e : history.getEntries()) {
            String date = e.getDate();
            int month = Integer.parseInt(date.split("-")[1]) - 1;
            monthCounts.set(month, monthCounts.get(month) + 1);
        }

        return monthCounts;
    }
}
