package finalproject;

/**
 *
 * An exception class to be thrown when a user attempts to do something
 * with their history, when they have none stored in the database.
 * @author Robin Ouzts 
 */
public class NoUserHistoryException extends Exception {

    /**
     * 
     * Construct the exception.
     */
    public NoUserHistoryException() {
        super();
    }

}
