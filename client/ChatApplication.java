import games.Hangman;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableArray;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatApplication extends Application {
    TextArea chatField;
    private BufferedReader chatReader;
    private BufferedWriter chatWriter;
    private VBox games = new VBox();
    private Button hangmanGame;

    @Override
    public void start(Stage stage)throws Exception{
        createUsername();

        Frame f = new Frame("Chat");
        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(chatField = new TextArea());
        chatField.setEditable(false);

        TextField message = new TextField("");

        hangmanGame = new Button("Hangman");

        games.getChildren().add(new Label("Games"));
        games.getChildren().add(hangmanGame);

        hangmanGame.setOnAction(event -> {
            Server.launchHangman();
        });


        message.setOnAction(event -> {
            String newMessage = message.getText();

            try{
                this.chatWriter.write(newMessage + "\n");
                this.chatWriter.flush();
            } catch (Exception e){
                throw new RuntimeException(e);
            }
            message.setText("");
        });

        new Thread(this::connectionHandler).start();

        borderPane.setLeft(games);
        borderPane.setBottom(message);
        borderPane.getBottom().setTranslateX(200);

        stage.setScene(new Scene(borderPane,1000,800));
        stage.show();
    }
    private void connectionHandler(){
        try {
            Socket socket = new Socket("localhost",1234);
            this.chatReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.chatWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            while(socket.isConnected()) {
                String message = chatReader.readLine();
                if(message.isEmpty()) {
                    continue;
                }
                System.out.println(message);
                Platform.runLater(() -> {
                    chatField.setText(chatField.getText() + message + "\n");

                });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void createUsername(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getDialogPane().setContent(new TextField("Gebruikersnaam"));

        Button usernameButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        usernameButton.setText("Zo heet ik!");

        Button cancelButton = (Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancelButton.setOnAction(event -> {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.getDialogPane().setContent(new Label("Je moet een gebruikersnaam invullen!"));
            error.show();
        });
        alert.showAndWait();
    }
}
