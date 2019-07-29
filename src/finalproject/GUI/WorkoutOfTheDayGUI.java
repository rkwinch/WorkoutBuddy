package finalproject.GUI;

import finalproject.InvalidUsernamePasswordException;
import finalproject.NoUserHistoryException;
import finalproject.ProjectController;
import finalproject.UserDB;
import finalproject.Utilities;
import finalproject.View;
import finalproject.WorkoutDuration;
import finalproject.WorkoutHistory;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * The main GUI class of the project. Extends JFrame and consists of many
 * JPanels; only one of which is the active content pane at a time. Implements
 * the View interface for the Model-View-Controller architecture.
 *
 * @author Robin Ouzts 
 */
public class WorkoutOfTheDayGUI extends JFrame implements View {

    /**
     *
     * Flag that is true if audio will play.
     */
    private Boolean audio_enabled;
    /**
     *
     * Component for the Chart Screen.
     */
    private ChartPanel chartPanel = null;
    /**
     *
     * Component for the Chart Selection screen.
     */
    private ChartSelectionPanel chartSelectionPanel;

    /**
     *
     * Reference to the Controller.
     */
    private final ProjectController controller;
    /**
     *
     * Component for the Workout History Display screen.
     */
    private WorkoutHistoryPanel historyPanel = null;
    /**
     *
     * Button to log in with.
     */
    private final JButton loginButton = new JButton("Login");

    /**
     *
     * Label used to display the WOTD logo.
     */
    private final JLabel logoLabel = new JLabel();

    /**
     *
     * The main, default JPanel (login screen).
     */
    private final JPanel mainPanel = new JPanel();
    /**
     *
     * A label for the password field.
     */
    private final JLabel passwordLabel = new JLabel("Password:");

    /**
     *
     * A field for the user to type their password in.
     */
    private final JPasswordField passwordTextField = new JPasswordField();

    /**
     *
     * The component for the Registration screen.
     */
    private RegistrationPanel reg = null;
    /**
     *
     * A button for the user to register with.
     */
    private final JButton registerButton = new JButton("Register");

    /**
     *
     * Flag to indicate if the user's data has been saved upon workout
     * completion.
     */
    private Boolean saved = false;

    /**
     *
     * Component for the Workout Selection screen.
     */
    private WorkoutSelectionPanel selectionPanel = null;
    /**
     *
     * Label for the username text field.
     */
    private final JLabel usernameLabel = new JLabel("Username:");
    /**
     *
     * Text field for the user to type their username into.
     */
    private final JTextField usernameTextField = new JTextField();

    /**
     *
     * Component for the Workout Completion screen.
     */
    private Container workoutCompletionPanel;

    /**
     *
     * Component for the main workout screen.
     */
    private WorkoutPanel workoutPanel = null;

    // End of variables declaration   
    /**
     *
     * Constructs a WorkoutOfTheDayGUI and displays the frame.
     *
     * @param controller A reference to the Controller being used.
     * @param audio_enabled A flag indicating if audio should be played.
     */
    public WorkoutOfTheDayGUI(ProjectController controller, Boolean audio_enabled) {
        this.controller = controller;
        this.audio_enabled = audio_enabled;
        CreateGUI();
    }

    /**
     *
     * Overrides the View's open() method. Sets the GUI to visible and sets the
     * x to close the application.
     */
    @Override
    public void open() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     *
     * Updates this GUI with data from the model.
     *
     * @param data A string to display in the Workout Panel.
     * @param duration Duration values to display in the Workout Panel.
     */
    @Override
    public void update(String data, WorkoutDuration duration) {
        if (workoutPanel != null) {
            workoutPanel.Update(data, duration);
        }
    }

    /**
     *
     * Creates the GUI by initializing subcomponents, setting the title, etc.
     */
    private void CreateGUI() {
        InitComponents();
        setContentPane(mainPanel);
        SetupLayout();
        setTitle("Workout of the Day");
        pack();
    }

    /**
     *
     * Initializes subcomponents, sets button action listeners, etc.
     */
    private void InitComponents() {
        logoLabel.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/resources/img/wotd.png")));

        loginButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            loginButtonActionPerformed(evt);
        });

        registerButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            registerButtonActionPerformed(evt);
        });

        usernameTextField.setText("Enter your username");
        passwordTextField.setText("Enter your password");

        usernameTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                usernameTextField.selectAll();
            }
        });

        passwordTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                passwordTextField.selectAll();
            }
        });

        this.getRootPane().setDefaultButton(loginButton);
    }

    /**
     *
     * Sets the layout for the JFrame.
     */
    private void SetupLayout() {
        GroupLayout layout = new GroupLayout(getContentPane());
        mainPanel.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(usernameLabel)
                        .addComponent(passwordLabel)
                        .addComponent(loginButton))
                .addComponent(logoLabel)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(usernameTextField)
                        .addComponent(passwordTextField)
                        .addComponent(registerButton)));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(logoLabel)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(usernameLabel)
                        .addComponent(usernameTextField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(passwordLabel)
                        .addComponent(passwordTextField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(loginButton)
                        .addComponent(registerButton)));
    }

//login button
    /**
     *
     * Callback function for the login button. Attempts to log the user in and
     * display the workout selection panel.
     *
     * @param evt Action triggering this callback.
     */
    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            if (Utilities.validateLogin(usernameTextField.getText(), new String(passwordTextField.getPassword()))) {
                selectionPanel = new WorkoutSelectionPanel(this);
                setContentPane(selectionPanel);
                pack();
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Failed to open user database.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Failed to read the user "
                    + "database.  Verify any users have been created, and if so,"
                    + " contact a developer for further assistance.");
        } catch (InvalidUsernamePasswordException ex) {
            JOptionPane.showMessageDialog(this, "Invalid username/password combination");
        }
    }

    /**
     *
     * Callback function for the register button. Attempts to display the
     * Registration panel.
     *
     * @param evt Action triggering this callback.
     */
    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {
        reg = new RegistrationPanel(this);
        setContentPane(reg);
        pack();
    }

    /**
     *
     * Creates the main workout panel based on data garnered from the Workout
     * Selection panel. Displays the panel maximized.
     *
     * @param durationSelection The duration selected.
     * @param difficultySelection The difficulty selected.
     */
    void CreateWorkoutPanel(String durationSelection, String difficultySelection) {
        controller.setModel(durationSelection, difficultySelection);
        setContentPane(new JPanel());

        this.setMinimumSize(new Dimension(800, 600));
        this.getContentPane().setLayout(new GridBagLayout());
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight());
        workoutPanel = new WorkoutPanel(durationSelection, difficultySelection, controller, this, audio_enabled);
        this.getContentPane().add(workoutPanel);
    }

    /**
     *
     * Attempts to display the chart selection panel.
     */
    void DisplayChartSelection() {
        chartSelectionPanel = new ChartSelectionPanel(this);
        setContentPane(chartSelectionPanel);
        pack();
    }

    /**
     *
     * Attempts to display charts showing user history.
     *
     * @param user1 The first user to get data from
     * @param user2 The second user to get data from
     * @param axisSelection A string representing the type of x-axis binning to
     * use.
     */
    void DisplayCharts(String user1, String user2, String axisSelection) {
        WorkoutHistory hist = null, hist2 = null;
        AxisType axis_type = null;

        try {
            hist = controller.GetHistory(user1);
            if ("By Weekday".equals(axisSelection)) {
                axis_type = new AxisType(AxisType.BY_WEEKDAY);
            } else if ("By Last 7 Days".equals(axisSelection)) {
                axis_type = new AxisType(AxisType.LAST_WEEK);
            } else if ("By Month".equals(axisSelection)) {
                axis_type = new AxisType(AxisType.BY_MONTH);
            };

        } catch (NoUserHistoryException ex) {
            JOptionPane.showMessageDialog(this, "User(s) don't have any history.");
            return;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Failed to open history database.");
            return;
        } catch (InvalidAxisTypeException ex) { // This should never happen
            JOptionPane.showMessageDialog(this, "Invalid axis type selection.");
            return;
        }

        try {
            hist2 = controller.GetHistory(user2);
        } catch (Exception ex) {
        }

        chartPanel = new ChartPanel(this, hist, hist2, axis_type);
        setContentPane(chartPanel);
        pack();
    }

    /**
     *
     * Displays workout history from a supplied parameter/user.
     *
     * @param hist The history to display.
     */
    void DisplayHistory(WorkoutHistory hist) {
        historyPanel = new WorkoutHistoryPanel(this, hist);
        setContentPane(historyPanel);
        pack();
    }

    /**
     *
     * Displays the workout selection panel.
     */
    void DisplaySelectionPanel() {
        selectionPanel = new WorkoutSelectionPanel(this);
        //  setContentPane(selectionPanel);
        setContentPane(selectionPanel);
        pack();
    }

    /**
     *
     * Upon completion of a workout, displays a completion screen and saves the
     * user's data.
     *
     * @param difficultySelection The difficulty of workout the user completed.
     * @param durationSelection The duration of workout the user completed.
     */
    void DisplayWorkoutCompletion(String difficultySelection, String durationSelection) {
        if (!saved) {
            workoutCompletionPanel = new WorkoutCompletionPanel(this, difficultySelection, durationSelection);
            setContentPane(workoutCompletionPanel);
            pack();
            saved = true;
            try {
                UserDB.getInstance().AddHistory(usernameTextField.getText(), difficultySelection, durationSelection);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Failed to open history database.");
            }
        }
    }

    /**
     *
     * Gets the history of the user whose name is currently part of the
     * usernameTextField.
     *
     * @return A WorkoutHistory object
     * @throws NoUserHistoryException Thrown if the username in the text field
     * has no history.
     * @throws IOException Thrown if the history file cannot be properly 
     * accessed.
     */
    WorkoutHistory GetUserHistory() throws NoUserHistoryException, IOException {
        return controller.GetHistory(usernameTextField.getText());
    }

    /**
     *
     * Restores the main panel to visibility.
     */
    void RestoreContentPane() {
        setContentPane(mainPanel);
        this.getRootPane().setDefaultButton(loginButton);
        pack();
    }

    /**
     * Gets the name currently entered in the username text field.
     * @return The name currently entered in the username text field.
     */
    String getUsername() {
        return usernameTextField.getText();
    }
}
