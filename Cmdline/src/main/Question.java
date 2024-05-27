package main;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a multiple-choice question.
 */
public class Question {
    private String content; // Question content
    private List<String> choices = new ArrayList<>(); // List of choices
    private String answer; // Correct answer

    // Constructor to initialize content and answer
    public Question(String content, String answer) {
        this.content = content;
        this.answer = answer;
    }

    public String getContent() { return content; } // Getter for content

    public void setContent(String content) { this.content = content; } // Setter for content

    public List<String> getChoices() { return choices; } // Getter for choices

    public void setChoices(List<String> choices) { this.choices = choices; } // Setter for choices

    public String getAnswer() { return answer; } // Getter for answer

    public void setAnswer(String answer) { this.answer = answer; } // Setter for answer

    // Method to add choices
    public void addChoice(String choice1, String choice2, String choice3, String choice4) {
        choices.add(choice1);
        choices.add(choice2);
        choices.add(choice3);
        choices.add(choice4);
    }
}
