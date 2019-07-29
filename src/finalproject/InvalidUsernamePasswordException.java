package finalproject;

/**
 *
 * An exception thrown during the login process when the username
 * and password do not result in a successful log in.
 * @author Robin Ouzts 
 */
public class InvalidUsernamePasswordException extends Exception {

    /**
     *
     * Constructor for this exception.
     */
    public InvalidUsernamePasswordException() {
        super();
    }
}
