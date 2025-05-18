import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentRecordTest {

    @Test
    public void testGetName() {
        StudentRecord student = new StudentRecord("John Doe", new ArrayList<>(), false);
        assertTrue("Student name should be 'John Doe'", student.getName().equals("John Doe"));
    }

    @Test
    public void testGetAverageWithScores() {
        List<Double> scores = Arrays.asList(90.0, 85.0, 95.0);
        StudentRecord student = new StudentRecord("Jane Smith", scores, false);
        double average = student.getAverage();
        assertTrue("Average should be 90.0", Math.abs(average - 90.0) < 0.001);
    }

    @Test
    public void testGetAverageWithNoScores() {
        StudentRecord student = new StudentRecord("Empty Student", new ArrayList<>(), false);
        double average = student.getAverage();
        assertTrue("Average with no scores should be 0.0", Math.abs(average) < 0.001);
    }

    @Test
    public void testHasError() {
        StudentRecord studentWithError = new StudentRecord("Error Student", new ArrayList<>(), true);
        assertTrue("Student should have error", studentWithError.hasError());

        StudentRecord studentWithoutError = new StudentRecord("Normal Student", new ArrayList<>(), false);
        assertTrue("Student should not have error", !studentWithoutError.hasError());
    }
}