package finalproject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * A Singleton class used to represent a database of users and passwords.
 * Also provides several useful functions related to storage of users
 * and their information.
 * @author Robin Ouzts 
 */
public class UserDB {

    /**
     *
     * The filename to be used for the user database.
     */
    private final static String filename = "userdb.txt";

    /**
     *
     * The filename to be used for the workout history database.
     */
    private final static String hist = "workouts.txt";

    /**
     *
     * The private instance of UserDB; used to enforce the Singleton design
     * pattern.
     */
    private static UserDB instance = null;

    /**
     *
     * The accessor for the Singleton instance of UserDB.
     * @return The (sole) instance of UserDB.
     */
    public static UserDB getInstance() { //singleton design pattern for only one DB
        if (instance == null) {
            instance = new UserDB();
        }
        return instance;
    }

    /**
     *
     * Constructor is private to enforce Singleton design pattern.
     */
    private UserDB() {
    }

    /**
     *
     * Adds a completed workout to a user's history.  Modifies the history
     * database in the process.
     * @param user The user that is being updated.
     * @param difficultySelection THe difficulty of the workout completed.
     * @param durationSelection The duration of the workout completed.
     * @throws IOException Thrown if the workout db can't properly be accessed.
     */
    public void AddHistory(String user, String difficultySelection, String durationSelection) throws IOException {
        List<String> newLines = new ArrayList<>();
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = dateFormat.format(date);
        try {
            Boolean foundUser = false;
            for (String line : Files.readAllLines(Paths.get(hist))) {
                if (line.contains(user)) {
                    //if the user is found in the database, append their new history to their
                    //previous history in the file by rewriting their past data and adding to the end 
                    foundUser = true;
                    newLines.add(line + " " + dateStr + "/" + difficultySelection + "/" + durationSelection.replaceAll("\\s", ""));
                } else {
                    //write what was previously there but with no additions
                    newLines.add(line);
                }
            }

            if (!foundUser) {
                //for a new user, add their data to the bottom of the file
                newLines.add(user + ": " + dateStr + "/" + difficultySelection + "/" + durationSelection.replaceAll("\\s", ""));
            }

            Files.write(Paths.get(hist), newLines); // actually write out the file here
        } catch (NoSuchFileException e) {
            //create the workout database if no database exists (so the first time anyone has saved anything to it)
            BufferedWriter writer = new BufferedWriter(new FileWriter(hist, true));

            writer.append(user + ": " + dateStr + "/" + difficultySelection + "/" + durationSelection.replaceAll("\\s", ""));
            writer.newLine();
            writer.close();
        }
    }

    /**
     *
     * Creates an account for a user and stores it in the user database file.
     * Assumes the username and password have already been validated.
     * @param username The username for the new user.
     * @param password The unencrypted password for the user. This value will 
     * be encrypted before storage.
     * @throws IOException If the user database file can't be properly accessed.
     */
    public void CreateAccount(String username, String password) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
        password = Utilities.getEncryptedPassword(password);

        writer.append(username + ":" + password);//':' is delimiter between usernames and passwords
        writer.newLine();
        writer.close();
    }

    /**
     *
     * Queries for the history for a specific user.
     * @param username The user to query history of.
     * @return A WorkoutHistory object representing the Workouts a user
     * has completed.
     * @throws FileNotFoundException If the history file can't be found.
     * @throws NoUserHistoryException If the user has no history.
     * @throws IOException If the history file can't properly be interacted with.
     */
    public WorkoutHistory QueryUserHistory(String username)
            throws FileNotFoundException, NoUserHistoryException, IOException {
        BufferedReader br;
        br = new BufferedReader(new FileReader(hist));

        String line;

        while ((line = br.readLine()) != null) {
            String[] data = line.split(":");

            if (data[0].equals(username)) {
                //username is found
                break;
            }
        }

        if (line == null) {
            //no such username 
            throw new NoUserHistoryException();
        }
        String[] data = line.split(": ");//separates username from the workouts
        if (!data[0].equals(username) || data.length != 2) {
            throw new NoUserHistoryException();
        }

        String[] history = data[1].split(" ");//splits all of the histories by spaces
        return new WorkoutHistory(username, history);
    }

    /**
     *
     * Validates a password is complex enough and has been correctly typed twice.
     * @param password The password the user entered.
     * @param confirmed The user's confirmation of their password.
     * @return True if the passwords are valid; false otherwise.
     * @throws PasswordComplexityException If the password lacks in complexity.
     * @throws PasswordMismatchException If the confirmed password is not equal
     * to the initial password.
     */
    public boolean ValidatePassword(String password, String confirmed) throws PasswordComplexityException, PasswordMismatchException {
        if (!password.equals(confirmed)) {
            throw new PasswordMismatchException("Passwords didn't match - try again");
        }

        if (!meetsComplexityRequirements(password)) {
            throw new PasswordComplexityException("Password doesn't meet "
                    + "complexity requirements (min. 8 characters, 1 number)");
        }

        return true;
    }

    /**
     *
     * Validates a username before registration is complete.
     * @param username The username to validate.
     * @return True if the username was validated, false otherwise.
     * @throws UserDBCorruptedException If the user DB appears to be in an
     * invalid state.
     * @throws DuplicateUserException If the username is already present in
     * the user db file.
     * @throws InvalidUsernameException If the username is invalid (currently,
     * only if it is an empty string).
     * @throws IOException If the user db can't properly be interacted with.
     */
    public boolean ValidateUsername(String username)
            throws UserDBCorruptedException,
            DuplicateUserException, InvalidUsernameException, IOException {

        if (username.length() == 0) {
            throw new InvalidUsernameException("No username entered - try again");
        }

        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException ex) {
            //creates user database if not already there
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
            writer.newLine();
            writer.close();
            return true;
        }

        String line;

        while ((line = br.readLine()) != null) {
            String[] data = line.split(":");
            if (data.length != 2) {
                throw new UserDBCorruptedException("Catastrophic error in validateUsername() - invalid user or password");
            }

            if (data[0].equals(username)) {
                throw new DuplicateUserException("Sorry, that username is already taken");
            }
        }

        return true;
    }

    /**
     *
     * Verifies if a string meets the password complexity requirements.
     * Currently, the requirements are length >= 8 and at least one number.
     * @param text The string to verify.
     * @return True if it meets the requirements, false otherwise.
     */
    private boolean meetsComplexityRequirements(String text) {
        if (text == null) {
            return false;
        }
        if (text.length() < 8) {
            return false;
        }

        /* Lower/upper/sym for future growth */
        int numeric = 0, lower = 0, upper = 0, sym = 0;
        for (char c : text.toCharArray()) {
            if (Character.isDigit(c)) {
                numeric++;
            }
            if (Character.isLowerCase(c)) {
                lower++;
            }
            if (Character.isUpperCase(c)) {
                upper++;
            } else {
                sym++;
            }
        }
        // Current criteria is minimum 1 numeric

        return numeric >= 1; // returns false if the password isn't long enough,
                             // if the password doesn't exist,
                             // and if < 1 numbers are in the password
    }

}
