package App;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

// Controller for handling translation interface actions
public class TranslateController implements Initializable {
    private String sourceLang = "en", targetLang = "vi"; // Default languages
    public Label english, vietnam; // Labels for languages

    public Button translateBtn, swapBtn; // Buttons for translating and swapping languages

    public TextArea translateTarget, translateExplain; // Text areas for source and translated text

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set up event handler for translate button
        translateBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                onClicktranslateBtn();
            }
        });

        // Enable/disable translate button based on input text
        translateTarget.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (translateTarget.getText().trim().isEmpty()) {
                    translateBtn.setDisable(true);
                } else {
                    translateBtn.setDisable(false);
                }
            }
        });

        // Set up event handler for swap button
        swapBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                onClickswapBtn();
            }
        });

        translateBtn.setDisable(true); // Initially disable translate button
    }

    @FXML
    public void onClicktranslateBtn() {
        try {
            TranslateAPI translateAPI = new TranslateAPI();
            String targetText = translateTarget.getText();
            String translatedText = translateAPI.translateText(targetText, sourceLang, targetLang);
            translateExplain.setText(translatedText);
        } catch (Exception e) {
            e.printStackTrace();
            translateExplain.setText("Translation Error");
        }
    }

    @FXML
    public void onClickswapBtn() {
        if (sourceLang.equals("en")) {
            sourceLang = "vi";
            targetLang = "en";
            vietnam.setLayoutX(126);
            english.setLayoutX(459);
        } else {
            sourceLang = "en";
            targetLang = "vi";
            vietnam.setLayoutX(459);
            english.setLayoutX(126);
        }
    }
}
