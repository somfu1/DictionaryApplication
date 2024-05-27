package App;

import MultiChoiceGame.ListQuestion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

// Controller for the game interface.
public class GameController implements Initializable {
    public ListQuestion questionBank = new ListQuestion(); // List of questions
    public int currentQuestionIndex = 0; // Current question index
    public String questionPath = "src/main/resources/Utils/question.txt"; // Path to the question file

    @FXML
    public Button checkBtn, nextBtn; // Buttons for checking and moving to next question
    @FXML
    public RadioButton choiceA, choiceB, choiceC, choiceD; // Radio buttons for answer choices
    @FXML
    public Label question; // Label to display the question
    @FXML
    public Label result; // Label to display the result

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        questionBank.insertFromCommandline(questionPath); // Load questions from the file
        displayQuestion(); // Display the first question
        nextBtn.setVisible(false); // Hide the next button initially
    }

    @FXML
    public void onClickcheckBtn(ActionEvent event) {
        nextBtn.setVisible(true); // Show the next button
        evaluateAnswer(); // Evaluate if the selected answer is correct
    }

    private void evaluateAnswer() {
        String correctAnswer = questionBank.get(currentQuestionIndex).getAnswer();
        boolean isAnswerCorrect = false;

        if ("A".equals(correctAnswer)) {
            isAnswerCorrect = choiceA.isSelected();
        } else if ("B".equals(correctAnswer)) {
            isAnswerCorrect = choiceB.isSelected();
        } else if ("C".equals(correctAnswer)) {
            isAnswerCorrect = choiceC.isSelected();
        } else if ("D".equals(correctAnswer)) {
            isAnswerCorrect = choiceD.isSelected();
        }

        if (isAnswerCorrect) {
            result.setTextFill(Color.GREEN);
            result.setText("Correct!");
        } else {
            result.setTextFill(Color.RED);
            result.setText("Incorrect!");
        }
    }

    private void displayQuestion() {
        question.setText(questionBank.get(currentQuestionIndex).getContent());
        choiceA.setText(questionBank.get(currentQuestionIndex).getChoices().get(0));
        choiceB.setText(questionBank.get(currentQuestionIndex).getChoices().get(1));
        choiceC.setText(questionBank.get(currentQuestionIndex).getChoices().get(2));
        choiceD.setText(questionBank.get(currentQuestionIndex).getChoices().get(3));
    }

    @FXML
    public void onClicknextBtn(ActionEvent event) {
        nextBtn.setVisible(false); // Hide the next button
        loadNextQuestion(); // Load the next question
    }

    private void loadNextQuestion() {
        currentQuestionIndex++;
        result.setText(""); // Clear the result label
        choiceA.setSelected(false); // Deselect choice A
        choiceB.setSelected(false); // Deselect choice B
        choiceC.setSelected(false); // Deselect choice C
        choiceD.setSelected(false); // Deselect choice D

        if (currentQuestionIndex >= questionBank.size()) { // If at the end of the list, reset to the first question
            currentQuestionIndex = 0;
        }

        displayQuestion(); // Display the next question
    }
}
