package finalproject;

/**
 *
 * An Exception thrown during registration when a username is
 * supplied that already exists in the user database.
 * @author Robin Ouzts 
 */
public class DuplicateUserException extends Exception {

    /**
     *
     * @param msg The message associated with this exception.
     */
    public DuplicateUserException(String msg) {
        super(msg);
    }

}
