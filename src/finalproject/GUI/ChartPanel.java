package finalproject.GUI;

import finalproject.WorkoutHistory;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * A JPanel representing bar charts that show workout history.
 * @author Robin Ouzts 
 */
public class ChartPanel extends JPanel {
    /**
     *
     * Button to go to the previous panel.
     */
    JButton back = new JButton("Back");

    /**
     *
     * Panel showing bar charts.
     */
    
    SimpleBarPanel barPanel;
    
    /**
     *
     * Button to go to the home screen.
     */
    JButton home = new JButton("Home");

    /**
     *
     * Panel representing a legend for the bar chart.
     */
    Legend legend;

    /**
     *
     * The main JFrame component.
     */
    WorkoutOfTheDayGUI parent;


    /**
     *
     * Constructs a ChartPanel.
     * @param parent A reference to the containing JFrame
     * @param hist1 A history for one user
     * @param hist2 An optional history for a second user
     * @param axis_type The type of x-axis to plot along.
     */
    ChartPanel(WorkoutOfTheDayGUI parent, WorkoutHistory hist1, WorkoutHistory hist2, AxisType axis_type) {
        this.parent = parent;
        InitComponents(hist1, hist2, axis_type);
        SetupLayout();
    }
    
    /**
     * Prunes data from a workout history according to the selected strategy.
     * @param strategy The strategy to use for pruning.
     * @param hist The history to prune.
     * @return Binned and pruned workout history data.
     */
    private ArrayList<Integer> GetEntries(EntryPrunerStrategy strategy, WorkoutHistory hist) {
        return strategy.executeStrategy(hist);
    }

    /**
     *
     * Initializes components of the JPanel.
     * @param hist The first workout history.
     * @param hist2 The optional second workout history.
     * @param x_axis The type of axes to use for x-axis labels and spacing.
     */
    private void InitComponents(WorkoutHistory hist, WorkoutHistory hist2, AxisType x_axis) {
        ArrayList<Integer> person1Data = null;
        ArrayList<Integer> person2Data = null;
        EntryPrunerStrategy strategy = null;

        back.addActionListener((java.awt.event.ActionEvent evt) -> {
            backButtonActionPerformed(evt);
        });

        home.addActionListener((java.awt.event.ActionEvent evt) -> {
            homeButtonActionPerformed(evt);
        });

        parent.getRootPane().setDefaultButton(home);

        if (x_axis.getType() == AxisType.BY_MONTH) {
            strategy = new ByMonthPruningStrategy();
        } else if (x_axis.getType() == AxisType.BY_WEEKDAY) {
            strategy = new ByWeekdayPruningStrategy();
        } else if (x_axis.getType() == AxisType.LAST_WEEK) {
            strategy = new ByLastWeekPruningStrategy();
        }

        person1Data = GetEntries(strategy, hist);
        person2Data = GetEntries(strategy, hist2);
        barPanel = new SimpleBarPanel(person1Data, person2Data, x_axis);

        String user1 = hist.getUsername();
        String user2 = "";
        if (hist2 != null) {
            user2 = hist2.getUsername();
        }
        legend = new Legend(user1, user2);
        this.setPreferredSize(new Dimension(barPanel.getSize().width + legend.getSize().width,
                barPanel.getSize().height + legend.getSize().height));
    }

    /**
     *
     * Defines the layout of components in this JPanel.
     */
    private void SetupLayout() {
        //using group layout
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addComponent(barPanel)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(back)
                        .addComponent(home)
                        .addComponent(legend)));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(barPanel)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(home)
                                .addComponent(back)
                                .addComponent(legend))));
    }


    /**
     *
     * Callback function for the back button.
     * @param evt The event triggering this callback.
     */
    private void backButtonActionPerformed(ActionEvent evt) {
        parent.DisplayChartSelection();
    }

    /**
     *
     * Callback function for the home button.
     * @param evt The event triggering this callback.
     */
    private void homeButtonActionPerformed(ActionEvent evt) {
        parent.RestoreContentPane();
    }

}
