import java.util.List;

/**
 * StudentRecord class stores a new student and their scores
 */
public class StudentRecord {
    private String name;
    private List<Double> scores;
    private boolean hasError;

    /**
     * Creates a new StudentRecord with a specified error status.
     *
     * @param name The student's name
     * @param scores A list of the student's scores
     * @param hasError Whether the student record has an error
     */
    public StudentRecord(String name, List<Double> scores, boolean hasError) {
        this.name = name;
        this.scores = scores;
        this.hasError = hasError;
    }

    /**
     * getter, gets name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Calculates and returns the average of all scores.
     * @return The average score, or 0.0 if no scores exist
     */
    public double getAverage() {
        if (scores == null || scores.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;
        for (Double score : scores) {
            sum += score;
        }

        return sum / scores.size();
    }

    /**
     * checks if this student record has an error
     * @return
     */
    public boolean hasError() {
        return hasError;
    }
}