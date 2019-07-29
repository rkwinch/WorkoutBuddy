package finalproject.GUI;

import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 *
 * A JPanel representing a Legend for a bar graph.
 * @author Robin Ouzts 
 */
public class Legend extends JPanel {

    /**
     *
     * The name of the first user.
     */
    private String user1;

    /**
     *
     * The name of the second user.
     */
    private String user2;

    /**
     *
     * A label for the first user's name.
     */
    JLabel user1Label = new JLabel();

    /**
     *
     * A label for the second user's name.
     */
    JLabel user2Label = new JLabel();

    /**
     *
     * Constructs a legend from two usernames.
     * @param user1 The first user's name.
     * @param user2 The second user's name.
     */
    public Legend(String user1, String user2) {
        this.user1 = user1;
        this.user2 = user2;

        InitComponents();
        SetupLayout();
        setBorder(new LineBorder(Color.BLACK));
    }

    /**
     *
     * Initializes components of the JPanel - sets colors, etc.
     */
    private void InitComponents() {
        user1Label.setForeground(Color.RED);
        user1Label.setText(user1);
        user2Label.setForeground(Color.BLUE);
        user2Label.setText(user2);
    }

    /**
     *
     * Sets up the layout for the JPanel.
     */
    private void SetupLayout() {
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(user1Label)
                        .addComponent(user2Label)));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(user1Label)
                .addComponent(user2Label));

        //  parent.add(this);
    }

}
