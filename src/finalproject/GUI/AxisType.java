package finalproject.GUI;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * A class representing the limits of an x-axis for a bar chart.
 * @author Robin Ouzts 
 */
class AxisType {

    /**
     *
     * A public static data field enumeration type for grouping data by month.
     */
    static final public int BY_MONTH = 0x1;

    /**
     *
     * A public static data field enumeration type for grouping data by weekday.
     */
    static final public int BY_WEEKDAY = 0x2;

    /**
     *
     * A public static data field enumeration type for grouping data from the previous week.
     */
    static final public int LAST_WEEK = 0x4;

    /**
     *
     * The type of this instance of AxisType (e.g. BY_WEEKDAY)
     */
    private int type;
    
    /**
     *
     * The labels for the axis points.
     */
    ArrayList<String> labels = new ArrayList<>();

    /**
     *
     * Constructs an AxisType of type type.
     * @param type The type (BY_MONTH, BY_WEEKDAY, etc.) of this axis.
     * @throws InvalidAxisTypeException If the type supplied is not currently
     * supported.
     */
    AxisType(int type) throws InvalidAxisTypeException {
        this.type = type;

        if (type == BY_MONTH) {
            labels.add("Jan");
            labels.add("Feb");
            labels.add("Mar");
            labels.add("Apr");
            labels.add("May");
            labels.add("Jun");
            labels.add("Jul");
            labels.add("Aug");
            labels.add("Sep");
            labels.add("Oct");
            labels.add("Nov");
            labels.add("Dec");

        } else if (type == BY_WEEKDAY) {
            labels.add("Sun");
            labels.add("Mon");
            labels.add("Tue");
            labels.add("Wed");
            labels.add("Thur");
            labels.add("Fri");
            labels.add("Sat");
        } else if (type == LAST_WEEK) {
            //get last 7 days based off of today
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            labels.add(LocalDate.now().format(formatter));
            labels.add(LocalDate.now().minusDays(1).format(formatter));
            labels.add(LocalDate.now().minusDays(2).format(formatter));
            labels.add(LocalDate.now().minusDays(3).format(formatter));
            labels.add(LocalDate.now().minusDays(4).format(formatter));
            labels.add(LocalDate.now().minusDays(5).format(formatter));
            labels.add(LocalDate.now().minusDays(6).format(formatter));
        } else {
            throw new InvalidAxisTypeException();
        }
    }

    /**
     *
     * Getter for the x-axis labels.
     * @return An array of strings representing labels for each point on
     * the axis.
     */
    public ArrayList<String> getLabels() {
        return labels;
    }

    /**
     *
     * Getter for the type of axis.
     * @return The enumeration representing the type of axis.
     */
    public int getType() {
        return type;
    }

}
