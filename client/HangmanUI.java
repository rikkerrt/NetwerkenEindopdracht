import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HangmanUI extends Application {

    private Stage hangmanStage;
    private TextField characterField;
    private Label guessingWordLabel;
    private VBox guessedLettersBox;
    private VBox livesBox;
    private Label livesLabel;

    @Override
    public void start(Stage primaryStage) {
        hangmanStage = primaryStage;
        hangmanStage.setTitle("Hangman");

        // Initialize UI components
        characterField = new TextField();
        guessingWordLabel = new Label("Guessing Word: _____"); // Placeholder text
        guessedLettersBox = new VBox();
        livesBox = new VBox();
        livesLabel = new Label("Lives: 5"); // Placeholder text

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(guessingWordLabel);
        borderPane.setCenter(guessedLettersBox);
        borderPane.setLeft(livesBox);
        borderPane.setBottom(characterField);
        borderPane.setRight(livesLabel);

        Scene scene = new Scene(borderPane, 600, 400);
        hangmanStage.setScene(scene);
        hangmanStage.show();
    }

    // Method to update UI components (example)
    private void updateGuessingWordLabel(String word) {
        Platform.runLater(() -> {
            guessingWordLabel.setText("Guessing Word: " + word);
        });
    }

    // Method to update lives count (example)
    private void updateLivesLabel(int lives) {
        Platform.runLater(() -> {
            livesLabel.setText("Lives: " + lives);
        });
    }

    // Method to launch HangmanUI
    public static void launchHangmanUI() {
        launch(); // Launches the JavaFX application
    }

    public static void main(String[] args) {
        launch(args); // Launches the JavaFX application
    }
}
