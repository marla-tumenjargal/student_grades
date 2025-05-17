import java.io.IOException;

/**
 * BadDataException is a custom checked exception that indicates malformed data in the input file.
 * This exception is thrown when there are issues with parsing data lines, such as missing lines,
 * bad integers, non-numeric scores, or leftover lines.
 *
 * @author Claude
 * @version 1.0
 * @since 2025-05-07
 */
public class BadDataException extends IOException {

    /**
     * Constructs a new BadDataException with the specified detail message.
     *
     * @param message The detail message
     */
    public BadDataException(String message) {
        super(message);
    }

    /**
     * Constructs a new BadDataException with the specified detail message and cause.
     *
     * @param message The detail message
     * @param cause The cause
     */
    public BadDataException(String message, Throwable cause) {
        super(message, cause);
    }
}