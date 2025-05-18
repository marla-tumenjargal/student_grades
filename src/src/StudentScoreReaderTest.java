import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class StudentScoreReaderTest {

    @Test
    public void testParseRecordsValidData() throws BadDataException {
        List<String> lines = Arrays.asList(
                "2",
                "John Doe",
                "3",
                "90.0",
                "85.0",
                "95.0",
                "Jane Smith",
                "2",
                "88.0",
                "92.0"
        );

        List<StudentRecord> students = StudentScoreReader.parseRecords(lines);
        assertTrue("Should parse 2 students", students.size() == 2);
        assertTrue("First student should be John Doe", students.get(0).getName().equals("John Doe"));
        assertTrue("Second student should be Jane Smith", students.get(1).getName().equals("Jane Smith"));
    }

    @Test
    public void testParseRecordsEmptyFile() {
        List<String> emptyLines = new ArrayList<>();
        boolean exceptionThrown = false;

        try {
            StudentScoreReader.parseRecords(emptyLines);
        } catch (BadDataException e) {
            exceptionThrown = true;
            assertTrue("Exception message should mention empty file", e.getMessage().contains("empty"));
        }

        assertTrue("BadDataException should be thrown for empty file", exceptionThrown);
    }

    @Test
    public void testParseRecordsInvalidFirstLine() {
        List<String> invalidLines = Arrays.asList("not a number", "John Doe");
        boolean exceptionThrown = false;

        try {
            StudentScoreReader.parseRecords(invalidLines);
        } catch (BadDataException e) {
            exceptionThrown = true;
            assertTrue("Exception message should mention first line", e.getMessage().contains("First line"));
        }

        assertTrue("BadDataException should be thrown for invalid first line", exceptionThrown);
    }
}