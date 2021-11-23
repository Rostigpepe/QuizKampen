package gameControllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    CatQuesAnsw cga = new CatQuesAnsw();

    public VBox background;
    public GridPane buttonPanel;

    @FXML
    private Button buttonA;
    @FXML
    private Button buttonB;
    @FXML
    private Button buttonC;
    @FXML
    private Button buttonD;
    @FXML
    private Label questionArea;

    @FXML
    private Text timeLeft;

    @FXML
    private Label genreField;

    @FXML
    Button hiddenButton;

    private final String[] questions = cga.getQuestions();
    private final String[] genre = cga.getGenre();
    private final String[][] answerOptions = cga.getAnswerOptions();
    private final String[] correctAnswers = cga.getCorrectAnswers();
    private int index;
    private String answer;
    private int correctGuesses = 0;
    private final int totalQuestions= Integer.parseInt(ChooseGameController.getTotalaFragorString());
    private int seconds;

    public int getCorrectGuesses() {
        return correctGuesses;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nextQuestion();
        startTimer();

    }

    private final Timeline timer = new Timeline();

    public void startTimer() {

        seconds = 10;

        timer.getKeyFrames().add(new KeyFrame(Duration.millis(1000), event -> {
            timeLeft.setText(String.valueOf(seconds));
            seconds--;

            boolean timeToChangeColor = seconds == 2;

            if (timeToChangeColor) {
                timeLeft.setStyle("-fx-fill:#a40404");
            }

            if (seconds < 0) {
                timeLeft.setStyle("-fx-fill:#00f400");
                timeLeft.setText("Times up!");
                displayAnswer();
            }

        }));

        timer.setCycleCount(Timeline.INDEFINITE);
        timer.playFromStart();

    }

    @FXML
    public void nextQuestion() {


        if (index >= totalQuestions) {
            timer.stop();
            hiddenButton.fire();
        }
        else {

            genreField.setText(genre[index]);
            questionArea.setText(questions[index]);
            buttonA.setText(answerOptions[index][0]);
            buttonB.setText(answerOptions[index][1]);
            buttonC.setText(answerOptions[index][2]);
            buttonD.setText(answerOptions[index][3]);
            timeLeft.setStyle("-fx-fill:#f8f7f7");
            timer.play();

        }
    }

    public void actionPerformed(ActionEvent e) {

        buttonA.setDisable(true);
        buttonB.setDisable(true);
        buttonC.setDisable(true);
        buttonD.setDisable(true);

        if (e.getSource() == buttonA) {
            answer = "A";
            if (answer.equals(correctAnswers[index])) {
                correctGuesses++;
            }
        }
        if (e.getSource() == buttonB) {
            answer = "B";
            if (answer.equals(correctAnswers[index])) {
                correctGuesses++;
            }
        }
        if (e.getSource() == buttonC) {
            answer = "C";
            if (answer.equals(correctAnswers[index])) {
                correctGuesses++;
            }
        }
        if (e.getSource() == buttonD) {
            answer = "D";
            if (answer.equals(correctAnswers[index])) {
                correctGuesses++;
            }
        }
        displayAnswer();

    }

    public void displayAnswer() {

        timer.stop();
        buttonA.setDisable(false);
        buttonB.setDisable(false);
        buttonC.setDisable(false);
        buttonD.setDisable(false);

        if (!correctAnswers[index].equals("A")) {
            buttonA.setStyle("-fx-background-color: #ff2f00;");
        } else {
            buttonA.setStyle("-fx-background-color: #00ff04;");
        }
        if (!correctAnswers[index].equals("B")) {
            buttonB.setStyle("-fx-background-color: #ff2f00;");
        } else {
            buttonB.setStyle("-fx-background-color: #00ff04;");
        }
        if (!correctAnswers[index].equals("C")) {
            buttonC.setStyle("-fx-background-color: #ff2f00;");
        } else {
            buttonC.setStyle("-fx-background-color: #00ff04;");
        }
        if (!correctAnswers[index].equals("D")) {
            buttonD.setStyle("-fx-background-color: #ff2f00;");
        } else {
            buttonD.setStyle("-fx-background-color: #00ff04;");
        }
        Timeline time = new Timeline(new KeyFrame(Duration.millis(2000), e -> {

            buttonA.setStyle("-fx-background-color: #23427F;");
            buttonB.setStyle("-fx-background-color: #23427F;");
            buttonC.setStyle("-fx-background-color: #23427F;");
            buttonD.setStyle("-fx-background-color: #23427F;");

            answer = " ";
            seconds = 10;
            timeLeft.setText(String.valueOf(seconds));
            buttonA.setDisable(false);
            buttonB.setDisable(false);
            buttonC.setDisable(false);
            buttonD.setDisable(false);
            index++;

            nextQuestion();


        }));
        time.play();

    }

    @FXML
    public void results(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(ChangeScreen.WAITING)));
        Stage primaryStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        primaryStage.setTitle("Quizkampen");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();

    }

}










