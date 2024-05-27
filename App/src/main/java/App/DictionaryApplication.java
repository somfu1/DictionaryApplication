package App;

import DictionaryCmdLine.Dictionary;
import DictionaryCmdLine.DictionaryManagement;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.input.ScrollEvent;
import javafx.stage.StageStyle;

// Main application class for the Dictionary application.
public class DictionaryApplication extends Application {
    private double offsetX;
    private double offsetY;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialize dictionary and dictionary management
        Dictionary dictionary = Dictionary.getInstance();
        DictionaryManagement dictionaryManagement = DictionaryManagement.getInstance();
        dictionaryManagement.insertFromCommandline(dictionary);

        // Load the main GUI
        Parent root = FXMLLoader.load(getClass().getResource("DictionaryGUI.fxml"));
        primaryStage.setTitle("Language Learning Application");
        primaryStage.initStyle(StageStyle.DECORATED);

        // Set up event handlers for dragging the window
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                offsetX = event.getSceneX();
                offsetY = event.getSceneY();
            }
        });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - offsetX);
                primaryStage.setY(event.getScreenY() - offsetY);
            }
        });

        // Set up the scene and stage
        Scene scene = new Scene(root);
        scene.setOnScroll(ScrollEvent::consume);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
