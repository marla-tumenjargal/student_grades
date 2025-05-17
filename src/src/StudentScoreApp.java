import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

/**
 * StudentScoreApp handles the file input/output operations for the student score processing application.
 * It prompts for input/output filenames, reads the input file, and writes results to the output file.
 *
 * @author Claude
 * @version 1.0
 * @since 2025-05-07
 */
public class StudentScoreApp {

    /**
     * The main method that runs the application.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> lines = null;

        // Get valid input file
        while (lines == null) {
            System.out.println("Enter input file name:");
            String inputFileName = scanner.nextLine();

            if (inputFileName.equalsIgnoreCase("quit")) {
                System.out.println("Program terminated.");
                scanner.close();
                return;
            }

            try {
                System.out.println("Reading lines...");
                Path path = Paths.get(inputFileName);
                lines = Files.readAllLines(path);
            } catch (FileNotFoundException e) {
                System.out.println("[Error] File not found. Try again or type 'quit' to exit...");
            } catch (Exception e) {
                System.out.println("[Error] " + e.getMessage() + ". Try again or type 'quit' to exit...");
            }
        }

        // Parse the lines
        List<StudentRecord> records = null;
        try {
            records = StudentScoreReader.parseRecords(lines);
        } catch (BadDataException e) {
            System.out.println("[Error] Bad data: " + e.getMessage());
        }

        // Write results to output file if parsing was successful
        if (records != null) {
            boolean outputSuccess = false;

            while (!outputSuccess) {
                System.out.println("Enter output file name to store results:");
                String outputFileName = scanner.nextLine();

                try (PrintWriter writer = new PrintWriter(new File(outputFileName))) {
                    for (StudentRecord record : records) {
                        if (record.hasError()) {
                            writer.println(record.getName() + " : [No scores or partial due to error]");
                        } else {
                            writer.println(record.getName() + " : " + record.getAverage());
                        }
                    }
                    System.out.println("Done writing averages to " + outputFileName);
                    outputSuccess = true;
                } catch (FileNotFoundException e) {
                    System.out.println("[Error] Cannot write to " + outputFileName + ". Try again.");
                }
            }
        }

        scanner.close();
    }
}