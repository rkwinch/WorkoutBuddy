package finalproject.GUI;

import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * A JPanel allowing the user to select chart options.
 *
 * @author Robin Ouzts 
 */
public class ChartSelectionPanel extends JPanel {

    /**
     *
     * Button to go to the previous panel.
     */
    JButton backButton = new JButton("Back");
    /**
     *
     * Button to display charts.
     */
    JButton button = new JButton("Display");

    /**
     *
     * Box to select date ranges.
     */
    JComboBox<String> comboBox;

    /**
     *
     * Simple label for comboBox.
     */
    JLabel label = new JLabel("Select date range: ");

    /**
     *
     * Reference to the containing JFrame.
     */
    WorkoutOfTheDayGUI parent;

    /**
     *
     * A textfield for the user to enter a name in.
     */
    JTextField user1Field = new JTextField("Enter the name of a user");
    /**
     *
     * An optional textfield for a second name.
     */
    JTextField user2Field = new JTextField("(Optional) Enter the name of a user to compare against");

    /**
     *
     * Creates a ChartSelectionPanel.
     *
     * @param parent A reference to the containing JFrame
     */
    public ChartSelectionPanel(WorkoutOfTheDayGUI parent) {
        this.parent = parent;
        InitComponents();
        SetupLayout();
    }

    /**
     *
     * Initializes the components of this panel; adds button action listeners,
     * etc.
     */
    private void InitComponents() {

        user1Field.setText(parent.getUsername());

        String[] options = new String[]{"By Weekday", "By Last 7 Days", "By Month"};
        comboBox = new JComboBox<>(options);
        comboBox.setEditable(false);

        button.addActionListener((java.awt.event.ActionEvent evt) -> {
            buttonActionPerformed(evt);
        });

        backButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            backButtonActionPerformed(evt);
        });

        user1Field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                user1Field.selectAll();
            }
        });

        user2Field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                user2Field.selectAll();
            }
        });

        parent.getRootPane().setDefaultButton(button);
    }

    /**
     *
     * Establishes the layout of this JPanel.
     */
    private void SetupLayout() {
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(label)
                        .addComponent(backButton))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(comboBox)
                        .addComponent(user1Field)
                        .addComponent(user2Field)
                        .addComponent(button)));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(comboBox)
                        .addComponent(label))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(user1Field)
                        .addComponent(backButton))
                .addComponent(user2Field)
                .addComponent(button));

    }

    /**
     *     
     * Callback function for the back button.
     * @param evt The event triggering this callback.
     */
    private void backButtonActionPerformed(ActionEvent evt) {
        parent.DisplaySelectionPanel();
    }

    /**
     *
     * Callback function for the display button.
     * @param evt The event triggering this callback.
     */
    private void buttonActionPerformed(ActionEvent evt) {
        parent.DisplayCharts(user1Field.getText(), user2Field.getText(), (String) comboBox.getSelectedItem());
    }
}
