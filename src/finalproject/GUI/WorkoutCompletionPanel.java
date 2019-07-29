package finalproject.GUI;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * A JPanel displayed upon completion of a workout.
 * @author Robin Ouzts 
 */
public class WorkoutCompletionPanel extends JPanel {
    /**
     *
     * Label used to display an icon with.
     */
    private final JLabel iconLabel = new JLabel();

    /**
     *
     * Reference to the containing JFrame.
     */
    private WorkoutOfTheDayGUI parent;

    /**
     *
     * A TextArea for displaying workout results in.
     */
    private JTextArea textArea = new JTextArea();

    /**
     *
     * A ScrollPane used to contain textArea.
     */
    private JScrollPane scrollPane;


    /**
     *
     * Constructs a WorkoutCompletionPanel.
     * @param parent The containing JFrame
     * @param difficulty The difficulty completed.
     * @param duration The duration completed.
     */
    public WorkoutCompletionPanel(WorkoutOfTheDayGUI parent, String difficulty, String duration) {
        this.parent = parent;
        CreatePanel(difficulty, duration);
    }

    /**
     *
     * Creates the Panel by initializing components and setting up the layout.
     * @param difficulty The difficulty completed.
     * @param duration The duration completed.
     */
    private void CreatePanel(String difficulty, String duration) {
        InitComponents(difficulty, duration);
        SetupLayout();
    }

    /**
     *
     * Initializes components of the panel; sets icons, text, font, etc.
     * @param difficulty The difficulty completed.
     * @param duration The duration completed.
     */
    private void InitComponents(String difficulty, String duration) {
        iconLabel.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/resources/img/wotd.png")));
        
        textArea.setEditable(false);
        textArea.setFont(textArea.getFont().deriveFont(36f));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        String text = "Congratulations! You have finished a workout! You did "
                + "the " + difficulty + " workout, which lasted " + duration
                + ".  Keep up the great work!\n\nYour data has been saved.";
        textArea.setText(text);
        
        scrollPane = new JScrollPane(textArea);
    }

    /**
     *
     * Sets up the layout of the JPanel.
     */
    private void SetupLayout() {
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(iconLabel)
                        .addComponent(scrollPane)));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(iconLabel)
                .addComponent(scrollPane));
    }

}
