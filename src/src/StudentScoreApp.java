import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
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
                System.out.println("Exiting program.");
                userInput.close();
                return;
            }

            try {
                File inputFile = new File(inputFileName);
                if (!inputFile.exists()) {
                    throw new FileNotFoundException("File not found: " + inputFileName);
                }

                List<String> lines = Files.readAllLines(Paths.get(inputFileName));

                List<StudentRecord> students;
                try {
                    students = StudentScoreReader.parseRecords(lines);
                    System.out.println("\nStudent Results:");
                    for (StudentRecord student : students) {
                        if (student.hasError()) {
                            System.out.println(student.getName() + " -- [No scores or partial due to error]");
                        } else {
                            System.out.println(student.getName() + " : " + student.getAverage());
                        }
                    }

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
}