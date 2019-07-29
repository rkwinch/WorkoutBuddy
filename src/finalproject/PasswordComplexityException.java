package finalproject;

/**
 *
 * An exception to be thrown during registration when the user's password
 * fails to meet the minimum password complexity requirements.
 * @author Robin Ouzts 
 */
public class PasswordComplexityException extends Exception {
    /**
     * Constructs the exception.
     * @param msg The exception's message
     */
    public PasswordComplexityException(String msg) {
        super(msg);
    }
}
