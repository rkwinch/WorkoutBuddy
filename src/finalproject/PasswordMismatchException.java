package finalproject;

/**
 *
 * An exception to be thrown when the user's password and confirmation
 * password do not match during registration.
 * @author Robin Ouzts 
 */
public class PasswordMismatchException extends Exception {
    /**
     *
     * Construct the exception.
     * @param msg The exception's message.
     */
    public PasswordMismatchException(String msg) {
        super(msg);
    }
}
