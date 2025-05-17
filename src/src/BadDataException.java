import java.io.IOException;

/**
 * thrown when there are issues with the format or content of the student data being processed
 */
public class BadDataException extends IOException {
    /**
     * Creates a new BadDataException with a specific error message.
     * @param message Description of the data error
     */
    public BadDataException(String message) {
        super(message);
    }
}
