package App;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

// Controller for handling the main dictionary application interface
public class DicController implements Initializable {
    @FXML
    private Tooltip tooltip1, tooltip2, tooltip3, tooltip4, tooltip5, tooltip6; // Tooltips for buttons

    @FXML
    private Button exitButton, searchButton, addButton, transButton, gameButton, homeButton; // Buttons for different actions

    @FXML
    private AnchorPane container; // Container for loading different components

    @FXML
    private ImageView home; // Image view for the home icon

    // Method to set the node (component) in the container
    private void setNode(Node node) {
        container.getChildren().clear();
        container.getChildren().add(node);
    }

    // Method to load and show a component from FXML file
    @FXML
    private void showComponent(String fxmlPath) {
        try {
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(fxmlPath));
            setNode(anchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to reload the home screen
    @FXML
    private void reloadHome() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("DictionaryGUI.fxml"));
            Scene currentScene = homeButton.getScene();
            Stage currentStage = (Stage) currentScene.getWindow();
            currentScene.setRoot(root);
            currentStage.setScene(currentScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set up button actions to load corresponding components
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showComponent("AddGUI.fxml");
            }
        });

        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showComponent("Search.fxml");
            }
        });

        transButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showComponent("Translate.fxml");
            }
        });

        gameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showComponent("MultigameGUI.fxml");
            }
        });

        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                reloadHome();
            }
        });

        // Set up exit button to close the application
        exitButton.setOnMouseClicked(e -> {
            System.exit(0);
        });
    }
}
