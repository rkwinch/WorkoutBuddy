package finalproject;

/**
 *
 * An exception thrown during registration when the username fails to meet
 * the validation criteria.
 * @author Robin Ouzts 
 */
public class InvalidUsernameException extends Exception {

    /**
     *
     * @param msg The exception's message.
     */
    public InvalidUsernameException(String msg) {
        super(msg);
    }

}
