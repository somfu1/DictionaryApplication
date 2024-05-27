package App;

import DictionaryCmdLine.Dictionary;
import DictionaryCmdLine.DictionaryManagement;
import DictionaryCmdLine.Word;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

// Controller for adding new words to the dictionary
public class AddController implements Initializable {
    private Dictionary dictionary = Dictionary.getInstance(); // Singleton instance of dictionary
    private DictionaryManagement dictionaryManagement = DictionaryManagement.getInstance(); // Singleton instance of dictionary management

    @FXML
    private TextArea addtarget, addexplain; // Text areas for entering target word and explanation
    @FXML
    private Button addbtn; // Button to add new word
    @FXML
    private Label successalert; // Label to show success message

    private String path = "src/main/resources/Utils/dictionary.txt"; // Path to dictionary file

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Disable add button initially if either text area is empty
        addbtn.setDisable(true);
        // Add key event handlers to enable/disable add button based on text area content
        EventHandler<KeyEvent> keyEventHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                addbtn.setDisable(addtarget.getText().isEmpty() || addexplain.getText().isEmpty());
            }
        };
        addtarget.setOnKeyTyped(keyEventHandler);
        addexplain.setOnKeyTyped(keyEventHandler);
        // Hide success alert initially
        successalert.setVisible(false);
    }

    @FXML
    private void handleaddbutton() {
        // Create a confirmation dialog
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Add Word");
        confirm.setHeaderText(null);
        confirm.setContentText("Bạn có chắc chắn muốn thêm từ này?");
        ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Huỷ", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirm.getButtonTypes().setAll(ok, cancel);
        Optional<ButtonType> optional = confirm.showAndWait();

        if (optional.isPresent() && optional.get() == ok) {
            String target = addtarget.getText().trim();
            String explain = addexplain.getText().trim();

            // Check if the word already exists in the dictionary
            if (dictionaryManagement.dictionaryLookup(target) != null) {
                // Create a warning dialog if the word already exists
                Alert warning = new Alert(Alert.AlertType.WARNING);
                warning.setTitle("Warning!");
                warning.setHeaderText(null);
                warning.setContentText("Từ đã có sẵn!\nBạn có thể chọn bổ sung hoặc thay thế nghĩa của từ đã nhập");
                ButtonType replaceExplain = new ButtonType("Replace");
                ButtonType addExplain = new ButtonType("Add");
                warning.getButtonTypes().setAll(replaceExplain, addExplain, ButtonType.CANCEL);
                Optional<ButtonType> optional1 = warning.showAndWait();

                if (optional1.isPresent()) {
                    if (optional1.get() == replaceExplain) {
                        dictionaryManagement.dictionaryUpdate_replace(dictionary, target, explain);
                        showSuccessAlert();
                    } else if (optional1.get() == addExplain) {
                        dictionaryManagement.dictionaryUpdate_add(dictionary, target, explain);
                        showSuccessAlert();
                    } else {
                        showInfoAlert("Thay đổi không được lưu!");
                    }
                }
            } else {
                // Add new word to the dictionary
                dictionaryManagement.dictionaryAdd(dictionary, target, explain);
                showSuccessAlert();
            }
            resetInputFields();
        }
    }

    // Show success alert and hide it after a delay
    private void showSuccessAlert() {
        successalert.setVisible(true);
        setDelay(() -> successalert.setVisible(false), 2000);
    }

    // Reset the input fields and disable the add button
    private void resetInputFields() {
        addbtn.setDisable(true);
        addtarget.setText("");
        addexplain.setText("");
    }

    // Show an informational alert with the specified message
    private void showInfoAlert(String message) {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle(null);
        infoAlert.setHeaderText(null);
        infoAlert.setContentText(message);
        infoAlert.showAndWait();
    }

    // Utility method to run a task after a specified delay
    private void setDelay(Runnable runnable, int delay) {
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
