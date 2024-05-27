package App;

import DictionaryCmdLine.Dictionary;
import DictionaryCmdLine.DictionaryManagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.polly.PollyClient;
import software.amazon.awssdk.services.polly.model.OutputFormat;
import software.amazon.awssdk.services.polly.model.SynthesizeSpeechRequest;
import software.amazon.awssdk.services.polly.model.SynthesizeSpeechResponse;
import software.amazon.awssdk.core.ResponseInputStream;

import javax.sound.sampled.*;

public class SearchController implements Initializable {
    private Dictionary dictionary = Dictionary.getInstance();
    private DictionaryManagement dictionaryManagement = DictionaryManagement.getInstance();
    private final String path = "src/main/resources/Utils/dictionary.txt";

    private ObservableList<String> wordList = FXCollections.observableArrayList();
    private int initialSearchIndex = 0;

    private Alert alertDialog = new Alert(Alert.AlertType.NONE);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayDefaultList(0);

        searchField.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (searchField.getText().isEmpty()) {
                    displayDefaultList(0);
                } else {
                    updateSearchResults();
                }
            }
        });

        explainField.setEditable(false);
        saveBtn.setVisible(false);
    }

    @FXML
    private void updateSearchResults() {
        wordList.clear();
        String searchKey = searchField.getText().trim();
        wordList = dictionaryManagement.dictionarySearcher(dictionary, searchKey);
        if (wordList.isEmpty()) {
            displayDefaultList(initialSearchIndex);
        } else {
            searchList.setItems(wordList);
        }
    }

    private void displayDefaultList(int startIndex) {
        wordList.clear();
        for (int i = startIndex; i < startIndex + 12; i++) {
            wordList.add(dictionary.get(i).getTarget());
        }
        searchList.setItems(wordList);
        searchedWord.setText(dictionary.get(startIndex).getTarget());
        explainField.setText(dictionary.get(startIndex).getDefinition());
    }

    @FXML
    private void handleClickSearchedWord(MouseEvent event) {
        String selectedWord = searchList.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            searchedWord.setText(selectedWord);
            explainField.setText(dictionaryManagement.dictionaryLookup(selectedWord));
            explainField.setVisible(true);
            explainField.setEditable(false);
        }
    }

    @FXML
    private void handleClickRemoveBtn() {
        ButtonType yesOption = new ButtonType("Có", ButtonBar.ButtonData.OK_DONE);
        ButtonType noOption = new ButtonType("Không", ButtonBar.ButtonData.CANCEL_CLOSE);
        alertDialog.setAlertType(Alert.AlertType.CONFIRMATION);
        alertDialog.getButtonTypes().setAll(yesOption, noOption);
        alertDialog.setTitle("Xác nhận");
        alertDialog.setHeaderText("Hãy thật sự chắc chắn nhé");
        alertDialog.setContentText("Bạn chắc chắn muốn xóa từ này?");
        alertDialog.showAndWait();

        if (alertDialog.getResult() == yesOption) {
            dictionaryManagement.dictionaryRemove(dictionary, searchedWord.getText());
            updateAfterRemoval();
            alertDialog.setAlertType(Alert.AlertType.INFORMATION);
            alertDialog.setTitle("Thông báo");
            alertDialog.setHeaderText(null);
            alertDialog.setContentText("Đã xóa thành công!");
            alertDialog.showAndWait();
        } else {
            alertDialog.setAlertType(Alert.AlertType.INFORMATION);
            alertDialog.setTitle("Thông báo");
            alertDialog.setContentText("Thao tác đã bị hủy");
            alertDialog.showAndWait();
        }
    }

    private void updateAfterRemoval() {
        for (int i = 0; i < wordList.size(); i++) {
            if (wordList.get(i).equals(searchedWord.getText())) {
                wordList.remove(i);
                break;
            }
        }
        searchList.setItems(wordList);
        explainField.setVisible(false);
    }

    @FXML
    private void handleClickEditBtn() {
        explainField.setEditable(true);
        saveBtn.setVisible(true);
        alertDialog.setAlertType(Alert.AlertType.INFORMATION);
        alertDialog.setTitle("Chế độ Chỉnh sửa từ");
        alertDialog.setContentText("Hãy viết những thay đổi bạn muốn vào ô bên dưới, và bấm nút trên góc phải để xác nhận");
        alertDialog.showAndWait();
    }

    @FXML
    private void handleClickSaveBtn() {
        ButtonType yesOption = new ButtonType("Có", ButtonBar.ButtonData.OK_DONE);
        ButtonType noOption = new ButtonType("Không", ButtonBar.ButtonData.CANCEL_CLOSE);
        alertDialog.setAlertType(Alert.AlertType.CONFIRMATION);
        alertDialog.getButtonTypes().setAll(yesOption, noOption);
        alertDialog.setTitle("Xác nhận");
        alertDialog.setHeaderText("Hãy thật sự chắc chắn nhé");
        alertDialog.setContentText("Bạn chắc chắn muốn cập nhật nghĩa của từ này?");
        alertDialog.showAndWait();

        if (alertDialog.getResult() == yesOption) {
            dictionaryManagement.dictionaryUpdate_replace(dictionary, searchedWord.getText(), explainField.getText());
            alertDialog.setAlertType(Alert.AlertType.INFORMATION);
            alertDialog.setTitle("Thông báo");
            alertDialog.setHeaderText(null);
            alertDialog.setContentText("Cập nhật thành công!");
            alertDialog.showAndWait();
        } else {
            alertDialog.setAlertType(Alert.AlertType.INFORMATION);
            alertDialog.setTitle("Thông báo");
            alertDialog.setContentText("Thao tác đã bị hủy");
            alertDialog.showAndWait();
        }
        saveBtn.setVisible(false);
        explainField.setEditable(false);
    }

    @FXML
    private void handleClickSoundBtn() {
        try {
            PollyClient polly = PollyClient.builder()
                    .region(Region.AP_SOUTHEAST_2)
                    .credentialsProvider(ProfileCredentialsProvider.create())
                    .build();

            SynthesizeSpeechRequest synthReq = SynthesizeSpeechRequest.builder()
                    .text(searchedWord.getText())
                    .voiceId("Joanna")
                    .outputFormat(OutputFormat.PCM)
                    .build();

            ResponseInputStream<SynthesizeSpeechResponse> synthRes = polly.synthesizeSpeech(synthReq);

            // Wrap the ResponseInputStream in BufferedInputStream
            InputStream audioStream = new BufferedInputStream(synthRes);

            // Define the audio format, assuming Amazon Polly returns PCM 16-bit mono at 16000 Hz
            AudioFormat format = new AudioFormat(16000, 16, 1, true, false);

            // Convert the InputStream to AudioInputStream
            AudioInputStream audioInputStream = new AudioInputStream(audioStream, format, AudioSystem.NOT_SPECIFIED);

            // Get a sound clip resource
            Clip clip = AudioSystem.getClip();

            // Open audio clip and load samples from the audio input stream
            clip.open(audioInputStream);

            // Start playing the audio clip
            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private TextField searchField;

    @FXML
    private ListView<String> searchList;

    @FXML
    private TextArea explainField;

    @FXML
    private Label searchedWord;

    @FXML
    private Button soundBtn, editBtn, removeBtn, saveBtn;
}
