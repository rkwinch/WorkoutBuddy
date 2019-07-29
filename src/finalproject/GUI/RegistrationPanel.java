package finalproject.GUI;

import finalproject.DuplicateUserException;
import finalproject.InvalidUsernameException;
import finalproject.PasswordComplexityException;
import finalproject.PasswordMismatchException;
import finalproject.UserDB;
import finalproject.UserDBCorruptedException;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * A JPanel to be displayed during the user registration process.
 *
 * @author Robin Ouzts 
 */
public class RegistrationPanel extends JPanel {

    /**
     *
     * A button to go to the previous panel.
     */
    private final JButton backButton = new JButton("Back");
    /**
     *
     * A field for the user to confirm their password in.
     */
    private final JPasswordField confirmPasswordTextField
            = new JPasswordField("Confirm Password: ");

    /**
     *
     * A button to create an account.
     */
    private final JButton createAccountButton = new JButton("Create Account");

    /**
     *
     * A label used to display the WOTD icon.
     */
    private final JLabel iconLabel = new JLabel();
    /**
     *
     * A reference to the containing JFrame.
     */
    private final WorkoutOfTheDayGUI parent;

    /**
     *
     * A text field used to enter the user's password in.
     */
    private final JPasswordField passwordTextField = new JPasswordField("Enter Password: ");
    /**
     *
     * A text field used to enter the username in.
     */
    private final JTextField usernameTextField
            = new JTextField("Enter Username: ");

    /**
     *
     * Constructs a RegistrationPanel.
     *
     * @param parent A reference to the containing JFrame
     */
    public RegistrationPanel(WorkoutOfTheDayGUI parent) {
        this.parent = parent;
        CreatePanel();
    }

    /**
     *
     * Creates this panel by instantiating subcomponents and establishing the
     * layout.
     */
    private void CreatePanel() {
        InitComponents();
        SetupLayout();
    }

    /**
     *
     * Initializes subcomponents of this panel; sets button action listeners,
     * etc.
     */
    private void InitComponents() {
        iconLabel.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/resources/img/wotd.png")));

        usernameTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                usernameTextFieldFocusGained(evt);
            }
        });

        createAccountButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            createAccountButtonActionPerformed(evt);
        });

        backButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            backButtonActionPerformed(evt);
        });

        passwordTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                passwordTextFieldFocusGained(evt);
            }
        });

        passwordTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passwordTextFieldKeyPressed(evt);
            }
        });

        confirmPasswordTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                confirmPasswordTextFieldFocusGained(evt);
            }
        });

        confirmPasswordTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                confirmPasswordTextFieldKeyPressed(evt);
            }
        });

        passwordTextField.setEchoChar((char) 0);
        confirmPasswordTextField.setEchoChar((char) 0);

        parent.getRootPane().setDefaultButton(createAccountButton);
    }

    /**
     *
     * Sets up the layout for this JPanel.
     */
    private void SetupLayout() {
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(iconLabel)
                        .addComponent(usernameTextField)
                        .addComponent(passwordTextField)
                        .addComponent(confirmPasswordTextField)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(backButton)
                                .addComponent(createAccountButton))));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(iconLabel)
                .addComponent(usernameTextField)
                .addComponent(passwordTextField)
                .addComponent(confirmPasswordTextField)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(backButton)
                        .addComponent(createAccountButton)));

    }

    /**
     *
     * A callback function for the back button.
     *
     * @param evt event triggering this callback.
     */
    private void backButtonActionPerformed(ActionEvent evt) {
        parent.RestoreContentPane();
    }

    /**
     *
     * A callback function for the password text field gaining focus. Used to
     * select all characters when the user clicks in the field.
     *
     * @param evt event triggering this callback.
     */
    private void confirmPasswordTextFieldFocusGained(java.awt.event.FocusEvent evt) {
        confirmPasswordTextField.selectAll();
    }

    /**
     *
     * A callback function for the confirm password text field being typed in.
     * Used to blank out the password.
     *
     * @param evt event triggering this callback.
     */
    private void confirmPasswordTextFieldKeyPressed(java.awt.event.KeyEvent evt) {
        confirmPasswordTextField.setEchoChar('*');
    }

    /**
     *
     * A callback function for the create account button being pressed.
     * Validates the username and password and creates the account if possible.
     *
     * @param evt event triggering this callback.
     */
    private void createAccountButtonActionPerformed(java.awt.event.ActionEvent evt) {
        UserDB db = UserDB.getInstance();
        try {
            if (db.ValidateUsername(usernameTextField.getText())
                    && db.ValidatePassword(String.valueOf(passwordTextField.getPassword()),
                            String.valueOf(confirmPasswordTextField.getPassword()))) {
                db.CreateAccount(usernameTextField.getText(), String.valueOf(passwordTextField.getPassword()));
                parent.RestoreContentPane();
            }
        } catch (UserDBCorruptedException ex) {
            JOptionPane.showMessageDialog(this, "UserDB has been corrupted; contact a developer for assistance.");
        } catch (DuplicateUserException ex) {
            JOptionPane.showMessageDialog(this, "Sorry, that username already exists.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Could not read from user database; contact a developer for assistance.");
        } catch (InvalidUsernameException ex) {
            JOptionPane.showMessageDialog(this, "Invalid username supplied.");
        } catch (PasswordComplexityException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } catch (PasswordMismatchException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    /**
     *
     * A callback function for the password text field gaining focus. Used to
     * select all characters when the field is clicked in.
     *
     * @param evt event triggering this callback.
     */
    private void passwordTextFieldFocusGained(java.awt.event.FocusEvent evt) {
        passwordTextField.selectAll();
    }

    /**
     *
     * A callback function for the password text field being typed in.
     * Used to blank out the password.
     * @param evt event triggering this callback.
     */
    private void passwordTextFieldKeyPressed(java.awt.event.KeyEvent evt) {
        passwordTextField.setEchoChar('*');
    }

     /**
     *
     * A callback function for the username text field gaining focus.
     * Used to select all characters when the field is clicked in.
     *
     * @param evt event triggering this callback.
     */
    private void usernameTextFieldFocusGained(java.awt.event.FocusEvent evt) {
        usernameTextField.selectAll();
    }

}
