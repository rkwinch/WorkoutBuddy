package finalproject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * A class consisting mainly of static methods; acting as generic
 * utilities that are useful in various parts of the application.
 * @author Robin Ouzts 
 */
public class Utilities {

    /**
     *
     * The flag for updating the model with a pause action.
     */
    public final static int TOGGLE_PAUSE = 0x1;

    /**
     *
     * The flag for updating the model with a stop action.
     */
    public final static int STOP_WORKOUT = 0x2;


    /**
     *
     * Takes a string and returns an encrypted version of it.
     * @param password The unencrypted password.
     * @return An encrypted version of password.
     */
    public static String getEncryptedPassword(String password) {
        MessageDigest messageDigest;
        //using messageDigest to encrypt the password using SHA-256 encryption
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("Critical error - encryption failure.");
        }
        byte[] digest = messageDigest.digest(password.getBytes());//array of encrypted bytes
        BigInteger no = new BigInteger(1, digest);
        return no.toString(16);//converting encryption to human readable string w/o colons
    }
    
    /**
     *
     * Validates a login and password match what's in the user database.
     * @param username The supplied username.
     * @param password The supplied unencrypted password.
     * @return True if login should be successful, false otherwise.
     * @throws InvalidUsernamePasswordException If the username and password
     * fail to match what's on file.
     * @throws FileNotFoundException If the user database can't be found.
     * @throws IOException If the user database can't be properly interacted
     * with.
     */
    public static boolean validateLogin(String username, String password) throws InvalidUsernamePasswordException, FileNotFoundException, IOException {
        String filename = "userdb.txt";
        password = getEncryptedPassword(password);

        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(":");
            if (data.length < 2) {
                throw new RuntimeException("Catastrophic error in validateLogin() - invalid user or password");
            }
            // Hash the supplied password and compare hashes

            String hashedPassword = "";
            for (int i = 1; i < data.length; i++) {
                hashedPassword += data[i];
            }
            if (data[0].equals(username) && hashedPassword.equals(password)) {
                return true;
            }
        }

        throw new InvalidUsernamePasswordException();
    }

    /**
     *
     * Private constructor to disable instantiation - this class consists
     * only of static helper methods and fields.
     */
    private Utilities() {
    }
}
