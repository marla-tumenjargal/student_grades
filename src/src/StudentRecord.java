import java.util.ArrayList;
import java.util.List;

/**
 * StudentRecord holds information about a student and their scores.
 * It provides methods to get the student's name and calculate their average score.
 *
 * @author Claude
 * @version 1.0
 * @since 2025-05-07
 */
public class StudentRecord {
    private String name;
    private List<Double> scores;
    private boolean hasError;

    /**
     * Constructor with name and scores.
     *
     * @param name The student's name
     * @param scores The student's scores
     */
    public StudentRecord(String name, List<Double> scores) {
        this.name = name;
        this.scores = scores != null ? scores : new ArrayList<>();
        this.hasError = false;
    }

    /**
     * Constructor with only name, initializes an empty scores list.
     *
     * @param name The student's name
     */
    public StudentRecord(String name) {
        this(name, new ArrayList<>());
    }

    /**
     * Gets the student's name.
     *
     * @return The student's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the average score of the student.
     * Returns 0 if there are no scores.
     *
     * @return The average score or 0 if no scores
     */
    public double getAverage() {
        if (scores.isEmpty()) {
            return 0;
        }

        double sum = 0;
        for (Double score : scores) {
            sum += score;
        }

        return sum / scores.size();
    }

    /**
     * Checks if the record has an error.
     *
     * @return true if the record has an error, false otherwise
     */
    public boolean hasError() {
        return hasError;
    }

    /**
     * Sets the error status of the record.
     *
     * @param hasError The error status to set
     */
    public void setError(boolean hasError) {
        this.hasError = hasError;
    }
}