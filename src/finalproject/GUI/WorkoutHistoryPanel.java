package finalproject.GUI;

import finalproject.WorkoutHistory;
import finalproject.WorkoutHistoryEntry;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * A JPanel used to display a user's workout history in text form.
 * @author Robin Ouzts 
 */
class WorkoutHistoryPanel extends JPanel {


    /**
     *
     * A button to go to the previous panel.
     */
    JButton backButton = new JButton("Back");
    /**
     *
     * A WorkoutHistory object representing the user's history.
     */
    WorkoutHistory hist;

    /**
     *
     * A TextArea to display the user's workout history.
     */
    JTextArea histText = new JTextArea();
    /**
     * 
     * A reference to the containing JFrame.
     */
    WorkoutOfTheDayGUI parent;

    /**
     *
     * Constructs the WorkoutHistoryPanel.
     * @param parent A reference to the containing JFrame
     * @param hist A WorkoutHistory object for this user.
     */
    WorkoutHistoryPanel(WorkoutOfTheDayGUI parent, WorkoutHistory hist) {
        this.hist = hist;
        this.parent = parent;
        CreatePanel();
    }

    /**
     *
     * Creates this JPanel by initializing subcomponents, setting the layout, etc.
     */
    private void CreatePanel() {
        InitComponents();
        SetupLayout();
    }

    /**
     *
     * Initializes subcomponents of this JPanel by setting up button listeners,
     * fonts, text, etc.
     */
    private void InitComponents() {
        backButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            backButtonActionPerformed(evt);
        });

        String display = "";
        for (WorkoutHistoryEntry e : hist.getEntries()) {
            display += "Date: " + e.getDate() + "\n";
            display += "Difficulty: " + e.getDifficulty() + "\n";
            display += "Duration: " + e.getDuration() + "\n";
            display += "----------------------\n";
        }

        histText.setText(display);
    }

    /**
     *
     * Sets up the layout of this JPanel.
     */
    private void SetupLayout() {
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(backButton)
                        .addComponent(histText)));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(backButton)
                .addComponent(histText));
    }

    /**
     *
     * A callback function for the back button. Restores the previous panel.
     * @param evt The action triggering this callback.
     */
    private void backButtonActionPerformed(ActionEvent evt) {
        parent.DisplaySelectionPanel();
    }

}
