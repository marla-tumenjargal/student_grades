import java.util.ArrayList;
import java.util.List;

/**
 * Handles validation of data format
 * doesn't do any file I/O operations directly bc it only processes data that has already been read into memory
 */
public class StudentScoreReader {
    /**
     *
     * @param lines list of String containing the raw text lines
     * @return list of StudentRecoord objects
     * @throws BadDataException if the file format is invalid (empty file or ir first line isn't integer, etC)
     */
    public static List<StudentRecord> parseRecords(List<String> lines) throws BadDataException {
        List<StudentRecord> students = new ArrayList<>();

        if (lines.isEmpty()) {
            throw new BadDataException("File is empty");
        }

        int currentLine = 0;
        try {
            int numStudents;
            try {
                numStudents = Integer.parseInt(lines.get(currentLine));
                currentLine++;
            } catch (NumberFormatException e) {
                throw new BadDataException("First line is not an integer");
            }

            for (int i = 0; i < numStudents; i++) {
                if (currentLine >= lines.size()) {
                    throw new BadDataException("Unexpected end of file");
                }

                String studentName = lines.get(currentLine);
                currentLine++;

                if (currentLine >= lines.size()) {
                    throw new BadDataException("Missing score count for student: " + studentName);
                }

                int numScores;
                boolean hasError = false;
                List<Double> scores = new ArrayList<>();

                try {
                    numScores = Integer.parseInt(lines.get(currentLine));
                    currentLine++;

                    for (int j = 0; j < numScores; j++) {
                        if (currentLine >= lines.size()) {
                            hasError = true;
                            break;
                        }

                        try {
                            double score = Double.parseDouble(lines.get(currentLine));
                            scores.add(score);
                        } catch (NumberFormatException e) {
                            hasError = true;
                        }
                        currentLine++;
                    }
                } catch (NumberFormatException e) {
                    hasError = true;
                    currentLine++;
                }

                students.add(new StudentRecord(studentName, scores, hasError));
            }

            return students;

        } catch (IndexOutOfBoundsException e) {
            throw new BadDataException("Unexpected end of file");
        }
    }
}