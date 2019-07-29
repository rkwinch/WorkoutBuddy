package finalproject;

/**
 *
 * An exception to be thrown if the user db appears to be in an
 * invalid state.
 * @author Robin Ouzts 
 */
public class UserDBCorruptedException extends Exception {

    /**
     * Constructor for the exception.
     * @param msg The message for the exception.
     */
    public UserDBCorruptedException(String msg) {
        super(msg);
    }

}
