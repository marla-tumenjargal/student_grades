import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Handles all input and output
 * Prompts the user for filenames
 * Creates a Scanner or reads all lines form the input file
 */
public class StudentScoreApp {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        boolean fileProcessed = false;

        while (!fileProcessed) {
            System.out.print("Enter input file name: ");
            String inputFileName = userInput.nextLine();

            if (inputFileName.equalsIgnoreCase("quit")) {
                userInput.close();
                return;
            }

            try {
                List<String> lines = readFile(inputFileName);
                System.out.println("Reading lines...");

                try {
                    List<StudentRecord> students = StudentScoreReader.parseRecords(lines);
                    for (StudentRecord student : students) {
                        if (student.hasError()) {
                            System.out.println("[Error] Bad data: Non-numeric score for " + student.getName());
                        }
                    }

                    System.out.print("Enter output file name to store results: ");
                    String outputFileName = userInput.nextLine();

                    writeResultsToFile(students, outputFileName);
                    System.out.println("Done -- " + outputFileName);
                    fileProcessed = true;

                } catch (BadDataException e) {
                    System.out.println("[Error] Bad data: " + e.getMessage());
                    System.out.print("Would you like to try a different file? (y/n): ");
                    String response = userInput.nextLine();
                    if (!response.equalsIgnoreCase("y")) {
                        fileProcessed = true;
                    }
                }

            } catch (FileNotFoundException | NoSuchFileException e) {
                System.out.println("[Error] File not found. Try again or type 'quit' to exit...");
            } catch (Exception e) {
                System.out.println("[Error] An error occurred: " + e.getMessage() + ". Try again or type 'quit' to exit...");
            }
        }
        userInput.close();
    }

    /**
     * Reads all lines from the specified file
     *
     * @param fileName Name of the file to read
     * @return List of strings, one per line in the file
     * @throws IOException If an I/O error occurs
     */
    private static List<String> readFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + fileName);
        }
        return Files.readAllLines(Paths.get(fileName));
    }

    /**
     * Writes student records to the specified output file
     *
     * @param students List of student records to write
     * @param outputFileName Name of the output file
     */
    private static void writeResultsToFile(List<StudentRecord> students, String outputFileName) {
        try (PrintWriter writer = new PrintWriter(outputFileName)) {
            for (StudentRecord student : students) {
                if (student.hasError()) {
                    writer.println(student.getName() + " : [No scores or partial due to error]");
                } else {
                    writer.println(student.getName() + " : " + student.getAverage());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("[Error] Could not create output file: " + e.getMessage());
        }
    }
}