package finalproject.GUI;

import finalproject.ProjectController;
import finalproject.Utilities;
import finalproject.WorkoutDuration;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * A JPanel displaying the main workout event loop.
 *
 * @author Robin Ouzts 
 */
public class WorkoutPanel extends JPanel {

    /**
     *
     * A location of an audio file to be played.
     */
    private URL audioFile;

    /**
     *
     * A flag representing if audio should be played.
     */
    private boolean audio_enabled;

    /**
     *
     * The current audio clip being used, or preparing to be used.
     */
    private Clip clip;
    /**
     *
     * Reference to the project controller.
     */
    private ProjectController controller;
    /**
     *
     * A label for the current date, to be displayed in the lower-left.
     */
    private JLabel dateLabel = new JLabel();

    /**
     *
     * A label for the duration of the workout.
     */
    private JLabel duration = new JLabel("", SwingConstants.CENTER);
    /**
     *
     * A label for the duration of the exercise.
     */
    private JLabel exDur = new JLabel();
    /**
     *
     * A label, used solely for an image icon, to represent the current workout.
     */
    private JLabel icon = new JLabel("", SwingConstants.CENTER);
    /**
     *
     * A label for which workout is being done.
     */
    private JLabel label = new JLabel("", SwingConstants.CENTER);
    /**
     *
     * A label for the top left to display the username.
     */
    private JLabel loggedInLabel = new JLabel("Logged in as:");

    /**
     *
     * A reference to the containing JFrame.
     */
    private WorkoutOfTheDayGUI parent;
    /**
     *
     * A button to pause execution.
     */
    private JButton pauseButton = new JButton();
    /**
     *
     * A label for the pause button.
     */
    private JLabel pauseLabel = new JLabel("Pause");

    /**
     *
     * A flag indicating if the execution is paused.
     */
    private boolean paused = false;

    /**
     *
     * A label for the total duration remaining.
     */
    private JLabel totalDuration = new JLabel();

    /**
     *
     * A label for the active user's name.
     */
    private JLabel usernameLabel = new JLabel();

    /**
     *
     * A label for the remaining duration on the exercise.
     */
    private JLabel wrkDur = new JLabel();
    /**
     *
     * Level of difficulty.
     */
    String difficultySelection;
    /**
     *
     * Workout duration, in minutes.
     */
    String durationSelection;

    /**
     *
     * Label for the exit button.
     */
    private JLabel exitLabel = new JLabel("Exit");

    /**
     *
     * A button to exit the workout without saving.
     */
    private JButton exitButton = new JButton();

    /**
     *
     * Creates a WorkoutPanel.
     *
     * @param durationSelection The selected duration.
     * @param difficultySelection The selected difficulty.
     * @param controller The project controller.
     * @param parent The containing JFrame.
     * @param audio_enabled A flag representing if audio should be used.
     */
    WorkoutPanel(String durationSelection, String difficultySelection,
            ProjectController controller, WorkoutOfTheDayGUI parent,
            Boolean audio_enabled) {
        this.parent = parent;
        this.controller = controller;
        this.difficultySelection = difficultySelection;
        this.durationSelection = durationSelection;
        this.audio_enabled = audio_enabled;
        InitComponents();
        SetupLayout();
    }

    /**
     *
     * Updates the WorkoutPanel with data from the model.  Changes the graphic,
     * instructions, and audio file.
     *
     * @param exercise The currently executing exercise.
     * @param remainingDuration The remaining duration on both the exercise and
     * workout.
     */
    public void Update(String exercise, WorkoutDuration remainingDuration) {
        if (exercise != null && exercise.equals("STOPPED")) {
            StopWorkoutDisplay();
        } else if (exercise != null && !exercise.equals(label.getText())) {
            try {
                label.setFont(new Font("Times New Roman", Font.PLAIN, 48));

                duration.setFont(new Font("Times New Roman", Font.PLAIN, 36));
                totalDuration.setFont(new Font("Times New Roman", Font.PLAIN, 36));
                dateLabel.setFont(new Font("Times New Roman", Font.PLAIN, 36));
                pauseLabel.setFont(new Font("Times New Roman", Font.PLAIN, 36));
                exitLabel.setFont(new Font("Times New Roman", Font.PLAIN, 36));
                wrkDur.setFont(new Font("Times New Roman", Font.PLAIN, 44));
                exDur.setFont(new Font("Times New Roman", Font.PLAIN, 44));
                loggedInLabel.setFont(new Font("Times New Roman", Font.PLAIN, 36));
                usernameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 44));
                label.setText(exercise);

                String iconFile = GetIconFilename(exercise);
                ImageIcon iconimg = new javax.swing.ImageIcon(getClass().
                        getResource(iconFile));
                Image scaled = getScaledImage(iconimg.getImage(), parent.getWidth() / 2, parent.getHeight() / 2);
                iconimg.setImage(scaled);
                icon.setIcon(iconimg);
                icon.setBorder(BorderFactory.createLineBorder(Color.black));
                label.setBorder(BorderFactory.createLineBorder(Color.black));
                exDur.setBorder(BorderFactory.createLineBorder(Color.black));
                wrkDur.setBorder(BorderFactory.createLineBorder(Color.black));
                duration.setBorder(BorderFactory.createLineBorder(Color.black));
                totalDuration.setBorder(BorderFactory.createLineBorder(Color.black));
                pauseButton.setBorder(BorderFactory.createLineBorder(Color.black));
                exitButton.setBorder(BorderFactory.createLineBorder(Color.black));
                duration.setBackground(Color.LIGHT_GRAY);
                totalDuration.setBackground(Color.LIGHT_GRAY);
                totalDuration.setOpaque(true);
                duration.setOpaque(true);
                label.setMinimumSize(new Dimension(parent.getWidth() / 2, 100));
                label.setPreferredSize(new Dimension(parent.getWidth() / 2, 100));
                label.setMaximumSize(new Dimension(parent.getWidth() / 2, 100));
                try {
                    audioFile = getClass().getResource(GetAudioFilename(exercise));
                    PlayAudio(audioFile);
                } catch (LineUnavailableException ex) {
                    JOptionPane.showMessageDialog(this, "Unable to play audio!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Unable to locate audio files!");
                } catch (UnsupportedAudioFileException ex) {
                    JOptionPane.showMessageDialog(this, "Preconfigured audio files are of an unsupported format!");
                } catch (IllegalStateException e) {
                    // This can sometimes happen if the GUI is closed/reopened quickly
                    // before an audio file is done playing; just ignore it.
                } catch (InvalidAudioFileException ex) {
                    // This can sometimes happen if the GUI is closed/reopened quickly
                    // before an audio file is done playing; just ignore it.
                }
            } catch (InvalidImageFileException ex) {
                // This can sometimes happen if the GUI is closed/reopened quickly
                // before an audio file is done playing; just ignore it.
            }
        }

        long workoutDur = (long) remainingDuration.getWorkoutDuration();
        long minutes = (workoutDur / 1000) / 60;
        int seconds = (int) ((workoutDur / 1000) % 60);
        String formatedDur = String.format("%.1fs", remainingDuration.getExerciseDuration());
        duration.setText("Remaining time on this exercise: ");
        exDur.setText(formatedDur);
        totalDuration.setText("Remaining duration in workout: ");
        wrkDur.setText(minutes + "m" + seconds + "s");
    }

    /**
     *
     * Gets the path to an audio file given a workout name.
     * @param str The name of the workout.
     * @return The name of the audio file corresponding to str.
     * @throws finalproject.GUI.InvalidAudioFileException If str does not
     * correspond to any known audio files.
     */
    private String GetAudioFilename(String str) throws InvalidAudioFileException {
        if ("WIDE GRIP PUSH-UPS".equals(str)) {
            return "/resources/audio/wgpu.wav";
        } else if ("CLOSE GRIP PUSH-UPS".equals(str)) {
            return "/resources/audio/cgpu.wav";
        } else if ("PLANKS".equals(str)) {
            return "/resources/audio/plk.wav";
        } else if ("HAND RELEASE PUSH-UPS".equals(str)) {
            return "/resources/audio/hrpu.wav";
        } else if ("PUSH-UPS".equals(str)) {
            return "/resources/audio/pu.wav";
        } else if ("SIT-UPS".equals(str)) {
            return "/resources/audio/su.wav";
        } else if ("JUMPING JACKS".equals(str)) {
            return "/resources/audio/jj.wav";
        } else if ("HIGH KNEES".equals(str)) {
            return "/resources/audio/hk.wav";
        } else if ("BURPEES".equals(str)) {
            return "/resources/audio/brp.wav";
        } else if ("SQUATS".equals(str)) {
            return "/resources/audio/squat.wav";
        } else if ("LUNGES".equals(str)) {
            return "/resources/audio/lunge.wav";
        } else if ("SQUAT JUMPS".equals(str)) {
            return "/resources/audio/sqj.wav";
        } else if ("BEAR CRAWLS".equals(str)) {
            return "/resources/audio/bc.wav";
        } else if ("REST".equals(str)) {
            return "/resources/audio/rest.wav";
        } else {
            throw new InvalidAudioFileException();
        }
    }

    /**
     *
     * Gets an image file given a workout type.
     * @param str A type of workout.
     * @return An image file corresponding to str.
     * @throws finalproject.GUI.InvalidImageFileException If no image file
     * corresponds to str.
     */
    private String GetIconFilename(String str) throws InvalidImageFileException {
        if ("WIDE GRIP PUSH-UPS".equals(str)) {
            return "/resources/img/wideGrip.jpg";
        } else if ("CLOSE GRIP PUSH-UPS".equals(str)) {
            return "/resources/img/closeGrip.png";
        } else if ("PLANKS".equals(str)) {
            return "/resources/img/plank.png";
        } else if ("HAND RELEASE PUSH-UPS".equals(str)) {
            return "/resources/img/handRelease.jpg";
        } else if ("PUSH-UPS".equals(str)) {
            return "/resources/img/pushUp.jpg";
        } else if ("SIT-UPS".equals(str)) {
            return "/resources/img/situp.jpg";
        } else if ("JUMPING JACKS".equals(str)) {
            return "/resources/img/jumpingJack.png";
        } else if ("HIGH KNEES".equals(str)) {
            return "/resources/img/highKnee.jpg";
        } else if ("BURPEES".equals(str)) {
            return "/resources/img/burpee.png";
        } else if ("SQUATS".equals(str)) {
            return "/resources/img/squat.jpg";
        } else if ("LUNGES".equals(str)) {
            return "/resources/img/lunge.jpg";
        } else if ("SQUAT JUMPS".equals(str)) {
            return "/resources/img/squatJump.jpg";
        } else if ("BEAR CRAWLS".equals(str)) {
            return "/resources/img/bearCrawl.jpg";
        } else if ("REST".equals(str)) {
            return "/resources/img/rest.jpeg";
        } else {
            throw new InvalidImageFileException();
        }
    }

    /**
     *
     * Initializes subcomponents of this panel - sets ActionListeners, icons,
     * fonts, etc.
     */
    private void InitComponents() {
        //setBorder(BorderFactory.createLineBorder(Color.black));
        pauseButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            pauseButtonActionPerformed(evt);
        });
        exitButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            exitButtonActionPerformed(evt);
        });
        usernameLabel.setText(parent.getUsername());
        dateLabel.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        ImageIcon icon = new javax.swing.ImageIcon(getClass().
                getResource("/resources/img/pause.png"));
        Image scaled = getScaledImage(icon.getImage(), 70, 70);
        icon.setImage(scaled);
        ImageIcon exitIcon = new javax.swing.ImageIcon(getClass().
                getResource("/resources/img/exit.png"));
        Image exitScaled = getScaledImage(exitIcon.getImage(), 70, 70);
        exitIcon.setImage(exitScaled);
        pauseButton.setIcon(icon);
        exitButton.setIcon(exitIcon);
    }

    /**
     *
     * Plays an audio file, if possible.
     * @param audioFile The audio file URL to play.
     * @throws LineUnavailableException If the system cannot get an audio line.
     * @throws IOException If the audio file can't be interacted with.
     * @throws UnsupportedAudioFileException If the audio file's exception isn't
     * supported on the system.
     */
    private void PlayAudio(URL audioFile) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        if (!audio_enabled) {
            return;
        }
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }

        AudioInputStream ais = AudioSystem.getAudioInputStream(audioFile);
        DataLine.Info info = new DataLine.Info(Clip.class, ais.getFormat());
        clip = (Clip) AudioSystem.getLine(info);
        clip.open(ais);

        clip.loop(0);
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
                        .addComponent(loggedInLabel)
                        .addComponent(dateLabel)
                        .addComponent(usernameLabel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(label)
                        .addComponent(icon)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(pauseLabel)
                                .addComponent(pauseButton))
                )
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(duration)
                        .addComponent(exDur)
                        .addComponent(totalDuration)
                        .addComponent(wrkDur)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(exitLabel))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(exitButton))
                        ))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(loggedInLabel)
                        .addComponent(duration))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(usernameLabel)
                        .addComponent(exDur))
                .addComponent(label)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(icon)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(totalDuration)
                                .addComponent(wrkDur)))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(pauseLabel)
                        .addComponent(pauseButton)
                        .addComponent(dateLabel)
                        .addComponent(exitButton)
                        .addComponent(exitLabel)));
        parent.add(this);
    }

    /**
     *
     * Stops display of the workout upon completion.  Saves the user's data.
     */
    private void StopWorkoutDisplay() {
        parent.DisplayWorkoutCompletion(difficultySelection, durationSelection);
    }

    /**
     *
     * Action handler for the exit button being pressed.
     * @param evt Action type that led to this callback.
     */
    private void exitButtonActionPerformed(ActionEvent evt) {
        if (clip != null) {
            if (clip.isOpen()) {
                clip.stop();
                clip.close();
            }
        }
        controller.updateModel(Utilities.STOP_WORKOUT);
        parent.DisplaySelectionPanel();
    }

    /**
     *
     * Scales an image to a certain dimension.  Used to enforce that all
     * workout images are of constant dimensions.
     * @param srcImg The Image object to scale.
     * @param w The width to scale srcImg to.
     * @param h The height to scale srcImg to.
     * @return srcImg scaled to (w,h)
     */
    private Image getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }

    /**
     *
     * Action handler for the pause button.  Pauses execution; tells the
     * model to pause.
     * @param evt The action that initiated this callback.
     */
    private void pauseButtonActionPerformed(java.awt.event.ActionEvent evt) {
        controller.updateModel(Utilities.TOGGLE_PAUSE);
        if (!paused) {
            if (clip != null) {
                clip.stop();
                clip.close();
            }
            ImageIcon icon = new javax.swing.ImageIcon(getClass().
                    getResource("/resources/img/playIcon.png"));
            Image scaled = getScaledImage(icon.getImage(), 70, 70);
            icon.setImage(scaled);
            pauseButton.setIcon(icon);
        } else {
            try {
                PlayAudio(audioFile);
            } catch (LineUnavailableException ex) {
                JOptionPane.showMessageDialog(this, "Unable to play audio!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Unable to locate audio files!");
            } catch (UnsupportedAudioFileException ex) {
                JOptionPane.showMessageDialog(this, "Preconfigured audio files are of an unsupported format!");
            } catch (IllegalStateException ex) {

            }
            ImageIcon icon = new javax.swing.ImageIcon(getClass().
                    getResource("/resources/img/pause.png"));
            Image scaled = getScaledImage(icon.getImage(), 70, 70);
            icon.setImage(scaled);
            pauseButton.setIcon(icon);
        }

        paused = !paused;
    }
}
