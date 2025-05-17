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
            int numStudents = Integer.parseInt(lines.get(currentLine));
            currentLine++;
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
                boolean studentHasError = false;

                try {
                    numScores = Integer.parseInt(lines.get(currentLine));
                    currentLine++;
                } catch (NumberFormatException e) {
                    currentLine++;
                    students.add(new StudentRecord(studentName, new ArrayList<>(), true));
                    continue;
                }

                List<Double> scores = new ArrayList<>();

                for (int j = 0; j < numScores; j++) {
                    if (currentLine >= lines.size()) {
                        studentHasError = true;
                        break;
                    }

                    try {
                        double score = Double.parseDouble(lines.get(currentLine));
                        scores.add(score);
                    } catch (NumberFormatException e) {
                        studentHasError = true;
                    }
                    currentLine++;
                }

                StudentRecord student = new StudentRecord(studentName, scores, studentHasError);
                students.add(student);
            }
            return students;

        } catch (NumberFormatException e) {
            throw new BadDataException("First line is not an integer");
        }
    }
}