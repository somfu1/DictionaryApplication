package main;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListQuestion extends ArrayList<Question> {
    public static String path = "../question.txt"; // Path to the file containing questions

    // Method to insert questions from a file
    public void insertFromCommandline() {
        File f = new File(path);
        try {
            // Read all lines from the file
            List<String> allText = Files.readAllLines(f.toPath(), StandardCharsets.UTF_8);

            // Iterate through the lines and create Question objects
            for (int i = 0; i < allText.size(); i += 6) {
                String content = allText.get(i);       // Question content
                String choice1 = allText.get(i + 1);   // First choice
                String choice2 = allText.get(i + 2);   // Second choice
                String choice3 = allText.get(i + 3);   // Third choice
                String choice4 = allText.get(i + 4);   // Fourth choice
                String answer = allText.get(i + 5);    // Correct answer

                // Create a new Question object
                Question question = new Question(content, answer);

                // Add choices to the question
                question.addChoice(choice1, choice2, choice3, choice4);

                // Add the question to the list
                this.add(question);
            }

            // Shuffle the questions to randomize their order
            Collections.shuffle(this);
        } catch (Exception e) {
            e.printStackTrace(); // Print the stack trace if an exception occurs
        }
    }
}