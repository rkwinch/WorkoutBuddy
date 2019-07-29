package finalproject.TUI;

import finalproject.InvalidUsernamePasswordException;
import finalproject.ProjectController;
import finalproject.UserDB;
import finalproject.Utilities;
import finalproject.View;
import finalproject.WorkoutDuration;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * A simple proof-of-concept for a TextUserInterface for WOTD, implementing the
 * View interface.
 *
 * @author Robin Ouzts 
 */
public class TextUserInterface implements View {

    /**
     *
     * A flag indicating if audio should play within the TUI.
     */
    private Boolean audio_enabled;
    /**
     *
     * The currently active audio clip.
     */
    private Clip clip;

    /**
     *
     * The project's controller.
     */
    private final ProjectController controller;
    /**
     *
     * A string representing the current workout.
     */
    private String currentDisplay = "";

    /**
     *
     * The workout's difficulty.
     */
    private String difficulty;

    /**
     *
     * The workout's duration.
     */
    private String duration;

    /**
     *
     * The username.
     */
    private String username;

    /**
     *
     * Constructs the TUI.
     *
     * @param controller The ProjectController; interacts with the model and
     * view.
     * @param audio_enabled Flag indicating if audio should be enabled.
     */
    public TextUserInterface(ProjectController controller, Boolean audio_enabled) {
        this.controller = controller;
        this.audio_enabled = audio_enabled;
    }

    /**
     *
     * Overridden method from the View interface. "Displays" the TUI by printing
     * the welcome message and prompting for registration/login.
     */
    @Override
    public void open() {
        PrintWelcomeMessage();
        String registerOrLogin = ProcessRegisterOrLogin();
        if ("register".equals(registerOrLogin)) {
            RegisterUser();
        } else {
            LoginUser();
        }
    }

    /**
     *
     * Updates the TUI with data from the model.
     *
     * @param data The current exercise to display.
     * @param duration The duration remaining on the exercise and workout.
     */
    @Override
    public void update(String data, WorkoutDuration duration) {
        if ("STOPPED".equals(data)) {
            StopPlayback();
        } else if (!currentDisplay.equals(data)) {

            currentDisplay = data;
            if (audio_enabled) {
                URL audioFile = getClass().getResource(GetAudioFilename(data));
                try {
                    PlayAudio(audioFile);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
            long idata = Math.round(duration.getExerciseDuration());
            System.out.println("Do " + data + " for " + idata + "s");
        }
    }

    /**
     *
     * Attempts to log the user in.
     *
     * @param username The user's username.
     * @param password The user's password (unencrypted).
     * @return
     */
    private Boolean AttemptLogin(String username, String password) {
        try {
            Utilities.validateLogin(username, password);
            return true;
        } catch (InvalidUsernamePasswordException ex) {
            System.out.println("Invalid username/password.  Try again.");
            return false;
        } catch (IOException ex) {
            System.out.println("Couldn't open user database - contact a dev");
            return false;
        }
    }

    /**
     *
     * Gets a filename for an audiofile corresponding to a workout type.
     *
     * @param str A workout type
     * @return An audio filename corresponding to str.
     */
    private String GetAudioFilename(String str) {
        if ("WIDE GRIP PUSH-UPS".equals(str)) {
            return "/resources/audio/wgpu.wav";
        } else if ("CLOSE GRIP PUSH-UPS".equals(str)) {
            return "/resources/audio/cgpu.wav";
        } else if ("PLANKS".equals(str)) {
            return "/resources/audio/plk.wav";
        } else if ("HAND RELEASE PUSH-UPS".equals(str)) {
            return "/resources/audio/hrpu.wav";
        } else if ("PUSH-UPS".equals(str)) {
            return "/resources/audio/su.wav";
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
            return "/resources/audio/bc.wav";
        } else {
            throw new RuntimeException("Invalid audio file!");
        }
    }

    /**
     *
     * Prompts the user to enter their password.
     *
     * @return The user's entered password.
     */
    private String GetPassword() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your password: ");
        return sc.next();
    }

    /**
     *
     * Prompts the user for the difficulty and duration they'd like for their
     * workout. Sets the model via the controller accordingly.
     */
    private void GetSelections() {
        difficulty = getDifficultySelection();
        duration = getDurationSelection();
        controller.setModel(duration, difficulty);
    }

    /**
     *
     * Prompts the user to enter a username.
     *
     * @return The username entered by the user.
     */
    private String GetUsername() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your username: ");
        return sc.next();
    }

    /**
     *
     * Prompts the user for a username and password until it authenticates, then
     * log them in.
     */
    private void LoginUser() {
        username = GetUsername();
        String password = GetPassword();

        while (!AttemptLogin(username, password)) {
            username = GetUsername();
            password = GetPassword();
        }

        GetSelections();
    }

    /**
     * Plays an audio file, if able.
     *
     * @param audioFile A URL for an audio file.
     * @throws LineUnavailableException If the system can't open an audio line.
     * @throws IOException If the system can't interact with the audio file.
     * @throws UnsupportedAudioFileException If the audio file is of an
     * unsupported extension type.
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
     * Prints a welcome message to the user.
     */
    private void PrintWelcomeMessage() {
        System.out.println("Welcome to Workout Of The Day!");
    }

    /**
     * Prompts the user to register or login until they enter one of those.
     * @return Either "register" or "login" depending on user input.
     */
    private String ProcessRegisterOrLogin() {
        Scanner sc = new Scanner(System.in);
        String input = "";
        while (!input.equals("register") && !input.equals("login")) {
            System.out.println("Do you need to [register] or [login]?");
            input = sc.next().toLowerCase();
        }
        return input;
    }

    /**
     * Prompts and guides the user through the registration process. If the
     * registration process fails for any reason, begin again.
     */
    private void RegisterUser() {
        String username = "", password = "", confirmed = "";
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter your desired username: ");
        username = sc.next();
        System.out.println("Enter your desired password: ");
        password = sc.next();
        System.out.println("Confirm your desired password: ");
        confirmed = sc.next();
        try {
            if (UserDB.getInstance().ValidateUsername(username)
                    && UserDB.getInstance().ValidatePassword(password, confirmed)) {
                UserDB.getInstance().CreateAccount(username, password);
                open();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            RegisterUser();
        }
    }

    /**
     *
     * Stops the workout in response to the model ending. Prints a congratulatory
     * message and saves the user's data.
     */
    private void StopPlayback() {
        String text = "Congratulations! You have finished a workout! You did "
                + "the " + difficulty + " workout, which lasted " + duration
                + " minutes.  Keep up the great work!\n\nYour data has been saved.";
        System.out.println(text);
        try {
            UserDB.getInstance().AddHistory(username, difficulty, duration);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        System.exit(0);
    }

    /**
     *
     * Gets the difficulty the user selected.
     * @return The difficulty the user entered.
     */
    private String getDifficultySelection() {
        String entry = "";
        Scanner sc = new Scanner(System.in);
        while (!"beginner".equals(entry) && !"advanced".equals(entry)) {
            System.out.println("What type of a workout would you like? Enter \"beginner\" or \"advanced\": ");
            entry = sc.next().toLowerCase();
        }
        return entry;
    }

    /**
     *
     * Gets the duration the user selected.
     * @return The duration the user selected.
     */
    private String getDurationSelection() {
        String entry = "     ";
        Scanner sc = new Scanner(System.in);
        while (!"15".equals(entry) && !"30".equals(entry)) {
            System.out.println("What duration of a workout would you like? Enter \"15\" or \"30\": ");
            entry = sc.next().toLowerCase();
        }
        return entry;
    }
}
