package finalproject.GUI;

import finalproject.NoUserHistoryException;
import finalproject.WorkoutHistory;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * The JPanel for selecting the workout.
 * @author Robin Ouzts 
 */
class WorkoutSelectionPanel extends JPanel {
    /**
     *
     * A RadioButton, part of the difficultyButtonGroup, for advanced difficulty.
     */
    private final JRadioButton advancedButton = new JRadioButton("Advanced");
    /**
     *
     * A button to go to the previous panel.
     */
    private final JButton backButton = new JButton("Back");

    /**
     *
     * A button to begin the workout.
     */
    private final JButton beginWorkoutButton = new JButton("Begin Workout");
    /**
     *
     * A RadioButton, part of the difficultyButtonGroup, for beginner difficulty.
     */
    private final JRadioButton beginnerButton = new JRadioButton("Beginner");
    /**
     *
     * A button to see the chart selection panel.
     */
    private JButton chartButton = new JButton("See charts!");

    /**
     *
     * A button group for difficulty settings.
     */
    private final ButtonGroup difficultyButtonGroup = new ButtonGroup();


    /**
     *
     * A label for difficulty settings.
     */
    private final JLabel difficultyLabel = new JLabel("Difficulty:");
    /**
     *
     * A button group for duration settings.
     */
    private final ButtonGroup durationButtonGroup = new ButtonGroup();


    /**
     *
     * A radio button, part of the durationButtonGroup, for a 15-min workout.
     */
    private final JRadioButton fifteenMinuteButton = new JRadioButton("15 min");
    /**
     *
     * A label used to display the wotd logo.
     */
    private final JLabel iconLabel = new JLabel();
    /**
     *
     * Reference to the containing JFrame
     */
    private final WorkoutOfTheDayGUI parent;

    /**
     *
     * A radio button, part of the durationButtonGroup, for a 30-min workout.
     */
    private final JRadioButton thirtyMinuteButton = new JRadioButton("30 min");
    /**
     *
     * A label used to display the Time string.
     */
    private final JLabel timeLabel = new JLabel("Time:");

    /**
     *
     * A button to view the user's history.
     */
    private final JButton viewHistory = new JButton("View your workout history");


    /**
     *
     * Creates the WorkoutSelectionPanel.
     * @param parent A reference to the containing JFrame
     */
    WorkoutSelectionPanel(WorkoutOfTheDayGUI parent) {
        this.parent = parent;
        CreatePanel();
        SetupLayout();
    }

    /**
     *
     * Creates the workout selection panel.
     */
    private void CreatePanel() {
        InitComponents();
    }

    /**
     *
     * Initializes subcomponents of the panel; sets up button groups, adds
     * action listeners, etc.
     */
    private void InitComponents() {
        difficultyButtonGroup.add(beginnerButton);
        difficultyButtonGroup.add(advancedButton);

        durationButtonGroup.add(fifteenMinuteButton);
        durationButtonGroup.add(thirtyMinuteButton);

        iconLabel.setIcon(new javax.swing.ImageIcon(getClass()
                .getResource("/resources/img/wotd.png")));

        beginWorkoutButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            beginWorkoutButtonActionPerformed(evt);
        });

        chartButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            chartButtonActionPerformed(evt);
        });

        backButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            backButtonActionPerformed(evt);
        });

        viewHistory.addActionListener((java.awt.event.ActionEvent evt) -> {
            viewHistoryButtonActionPerformed(evt);
        });

        beginnerButton.doClick();
        fifteenMinuteButton.doClick();

        parent.getRootPane().setDefaultButton(beginWorkoutButton);
    }


    /**
     *
     * Sets the layout for this panel.
     */
    private void SetupLayout() {
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(difficultyLabel)
                        .addComponent(beginnerButton)
                        .addComponent(advancedButton)
                        .addComponent(backButton)
                        .addComponent(beginWorkoutButton))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(iconLabel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(timeLabel)
                        .addComponent(fifteenMinuteButton)
                        .addComponent(thirtyMinuteButton)
                        .addComponent(viewHistory)
                        .addComponent(chartButton)));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(iconLabel)
                        .addComponent(backButton))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(difficultyLabel)
                        .addComponent(timeLabel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(beginnerButton)
                        .addComponent(fifteenMinuteButton))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(advancedButton)
                        .addComponent(thirtyMinuteButton))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(viewHistory)
                        .addComponent(beginWorkoutButton))
                .addComponent(chartButton));

    }


    /**
     *
     * Action handler for the back button. Restores the previous panel.
     * @param evt The event that triggered this handler.
     */
    private void backButtonActionPerformed(ActionEvent evt) {
        parent.RestoreContentPane();
    }
    /**
     *
     * Action handler for the begin workout button. Displays the workout panel.
     * @param evt The event that triggered this handler.
     */
    private void beginWorkoutButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String durationSelection = getButtonValue(durationButtonGroup);
        String difficultySelection = getButtonValue(difficultyButtonGroup);
        parent.CreateWorkoutPanel(durationSelection, difficultySelection);
    }

    /**
     *
     * Action handler for the chart  button. Displays the chart selection panel.
     * @param evt The event that triggered this handler.
     */
    private void chartButtonActionPerformed(ActionEvent evt) {
        parent.DisplayChartSelection();
    }
    /**
     *
     * Gets the currently selected RadioButton from a ButtonGroup by iterating
     * over the buttons and returning the text of the one that is selected.
     * @param grp A button group to iterate over.
     * @return The text that corresponds to the button in grp that is selected.
     */
    private String getButtonValue(ButtonGroup grp) {
        for (Enumeration<AbstractButton> buttons = grp.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }
    /**
     *
     * Action handler for the view history button. Displays the history panel.
     * @param evt The event that triggered this handler.
     */
    private void viewHistoryButtonActionPerformed(ActionEvent evt) {
        try {
            WorkoutHistory hist = parent.GetUserHistory();
            parent.DisplayHistory(hist);
        } catch (NoUserHistoryException ex) {
            JOptionPane.showMessageDialog(this, "Sorry, you have no workout history yet.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "History file cannot be located. Contact a developer for assistance");
        }
    }
}
