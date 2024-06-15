import games.Hangman;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ChatApplication extends Application {
    TextArea chatField;
    private BufferedReader chatReader;
    private BufferedWriter chatWriter;
    private VBox games = new VBox();
    private Button hangmanGame;

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(chatField = new TextArea());
        chatField.setEditable(false);

        TextField message = new TextField("");

        hangmanGame = new Button("Hangman");

        games.getChildren().add(new Label("Games"));
        games.getChildren().add(hangmanGame);

        new Thread(this::connectionHandler).start();

        hangmanGame.setOnAction(event -> {
            new Hangman();
        });

        message.setOnAction(event -> {
            String newMessage = message.getText();

            try {
                this.chatWriter.write(newMessage + "\n");
                this.chatWriter.flush();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            message.clear();
        });

        borderPane.setLeft(games);
        borderPane.setBottom(message);
        borderPane.getBottom().setTranslateX(200);

        stage.setScene(new Scene(borderPane, 1000, 800));
        stage.show();
    }

    private void connectionHandler() {
        try {
            Socket socket = new Socket("localhost", 1234);
            this.chatReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.chatWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            while (socket.isConnected()) {
                String message = chatReader.readLine();
                if (message.isEmpty()) {
                    continue;
                }

                System.out.println(message);
                Platform.runLater(() -> {
                    chatField.setText(chatField.getText() + message + "\n");

                });
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() throws Exception {
        // Perform any cleanup tasks here before exiting the application
        System.out.println("Performing cleanup tasks...");
        System.exit(0);
    }
}
