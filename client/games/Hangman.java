package games;

import com.sun.javaws.jnl.RContentDesc;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Hangman {
    public List<String> hangmanWords = new ArrayList<>();
    private String wordToGuess = "";
    private String wordBuild = "";
    private TextField character = new TextField("");
    private ArrayList<Character> guessedList = new ArrayList<>();
    private Label guessingWord = new Label();
    private VBox guessedletters = new VBox();
    private VBox amountOfLives = new VBox();
    private ArrayList<String> wrongLetters = new ArrayList<>();
    private Label livesLabel = new Label();
    private int lives = 7;
    private Stage hangmanStage;

    public Hangman() {
        launchStage();
    }

    public void launchStage() {
        createWords();
        pickWord();
        playing();

        hangmanStage = new Stage();

        character.setEditable(true);
        guessedletters.getChildren().add(new Label("Foute letters"));
        amountOfLives.getChildren().add(new Label("Hoeveelheid levens:"));
        livesLabel.setText(Integer.toString(lives));
        amountOfLives.getChildren().add(livesLabel);

        BorderPane borderPane = new BorderPane();
        borderPane.setRight(amountOfLives);
        borderPane.setLeft(guessedletters);
        borderPane.setCenter(guessingWord);
        borderPane.setBottom(character);

        Scene hangmanScene = new Scene(borderPane);
        hangmanStage.setScene(hangmanScene);


        hangmanStage.setHeight(750);
        hangmanStage.setWidth(1000);
        hangmanStage.setTitle("Hangman");
        hangmanStage.show();
    }

    public void createWords() {
        hangmanWords.add("computer");
        hangmanWords.add("atix");
        hangmanWords.add("technisch");
        hangmanWords.add("informatica");
        hangmanWords.add("avans");
        hangmanWords.add("programmeren");
        hangmanWords.add("toetsweek");
        hangmanWords.add("android");
        hangmanWords.add("galgje");
        hangmanWords.add("gamen");
        hangmanWords.add("laptop");
        hangmanWords.add("weerstation");
        hangmanWords.add("boebot");
        hangmanWords.add("festivalplanner");
        hangmanWords.add("beleving");
        hangmanWords.add("netwerken");
        hangmanWords.add("lovensedijkstraat");
        hangmanWords.add("opleiding");
        hangmanWords.add("foutmelding");
        hangmanWords.add("chatbox");
    }

    public String builder = "";

    private void pickWord() {
        Random ran = new Random();
        this.wordToGuess = hangmanWords.get(ran.nextInt(hangmanWords.size() - 1));
        System.out.println(wordToGuess);

        for (int i = 0; i < wordToGuess.length(); i++) {
            builder = builder + "_";
        }
        guessingWord.setText(builder);

    }

    public List<String> getHangmanWords() {
        return this.hangmanWords;
    }

    private void playing() {
        character.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                playLetter(character.getCharacters().toString());
            }
        });

    }

    public void playLetter(String guess) {

        guess = guess.toLowerCase();
        Character letter = guess.charAt(0);

        if (guessedList.contains(letter)) {
            return;
        }

        String newWord = "";
        guessedList.add(letter);
        //check of de letter erin zit en goed geraden is
        for (int i = 0; i < wordToGuess.length(); i++) {
            if (wordToGuess.charAt(i) == letter) {
                newWord += letter;
            } else if (builder.charAt(i) != '_') {
                newWord += builder.charAt(i);
            } else {
                newWord += '_';
            }
        }

        //verander her label naar het geupdate woord
        builder = newWord;
        guessingWord.setText(builder);
        if (builder.equals(wordToGuess)) {
            System.out.println("gg ez");
            //game end
        }

        System.out.println("lekker bezig pik");

        if (!(wordToGuess.contains(letter))) {
            if (wrongLetters.contains(guess)) {
            }
            lives--;
            wrongLetters.add(guess);
            guessedletters.getChildren().add(new Label(guess));
            livesLabel.setText(Integer.toString(lives));
            System.out.println("noob");
        }
        if (lives == 0)

            createDialogScreen();

    }

    public void createDialogScreen() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.contentTextProperty().setValue("Geen levens meer!\n\nNieuwe game?");

        Button restartButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        restartButton.setText("Restart");
        Button noRestartButton = (Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL);
        noRestartButton.setText("Nopers!");

        restartButton.setOnAction(event1 -> {
            hangmanStage.close();
            new Hangman();
        });

        noRestartButton.setOnAction(event1 -> {
            hangmanStage.close();
        });
        alert.showAndWait();
    }

}
