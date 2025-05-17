import java.util.ArrayList;
import java.util.List;

/**
 * StudentScoreReader handles parsing raw file content into StudentRecord objects.
 * It validates data format and throws BadDataException when formatting issues are detected.
 *
 * @author Claude
 * @version 1.0
 * @since 2025-05-07
 */
public class StudentScoreReader {

    /**
     * Parses a list of lines into a list of StudentRecord objects.
     *
     * @param lines The raw lines from the input file
     * @return A list of StudentRecord objects
     * @throws BadDataException if data format is invalid
     */
    public static List<StudentRecord> parseRecords(List<String> lines) throws BadDataException {
        if (lines == null || lines.isEmpty()) {
            throw new BadDataException("Input file is empty");
        }

        // Parse the first line to get number of students
        int numStudents;
        try {
            numStudents = Integer.parseInt(lines.get(0).trim());
        } catch (NumberFormatException e) {
            throw new BadDataException("First line is not an integer");
        }

        if (numStudents < 0) {
            throw new BadDataException("Number of students cannot be negative");
        }

        List<StudentRecord> records = new ArrayList<>();
        int currentLine = 1; // Start after the first line

        // Process each student
        for (int i = 0; i < numStudents; i++) {
            if (currentLine >= lines.size()) {
                throw new BadDataException("Not enough lines for all students");
            }

            // Get student name
            String name = lines.get(currentLine++);

            // Get number of scores
            int numScores;
            try {
                if (currentLine >= lines.size()) {
                    throw new BadDataException("Missing score count for student: " + name);
                }
                numScores = Integer.parseInt(lines.get(currentLine++).trim());
                if (numScores < 0) {
                    throw new BadDataException("Number of scores cannot be negative for student: " + name);
                }
            } catch (NumberFormatException e) {
                StudentRecord record = new StudentRecord(name);
                record.setError(true);
                records.add(record);
                throw new BadDataException("Non-numeric score count for " + name);
            }

            // Parse scores
            List<Double> scores = new ArrayList<>();
            boolean hasError = false;

            for (int j = 0; j < numScores; j++) {
                if (currentLine >= lines.size()) {
                    throw new BadDataException("Not enough score lines for student: " + name);
                }

                try {
                    double score = Double.parseDouble(lines.get(currentLine++).trim());
                    scores.add(score);
                } catch (NumberFormatException e) {
                    hasError = true;
                    currentLine++; // Skip this score
                    throw new BadDataException("Non-numeric score for " + name);
                }
            }

            // Create student record
            StudentRecord record = new StudentRecord(name, scores);
            if (hasError) {
                record.setError(true);
            }
            records.add(record);
        }

        // Check if there are extra lines
        if (currentLine < lines.size()) {
            throw new BadDataException("Extra lines found in input file");
        }

        return records;
    }
}