package gameControllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.text.FontWeight.BOLD;

public class GameController implements Initializable {


    public VBox background;
    public GridPane buttonPanel;


    String[] questions = {
            "Vad heter huvudstaden i Brasilien? ", "Vem är president i USA?"


    };
    String[] genre = {"Geografi", "Samhällskunskap", "Naturvetenskap"};


    String[][] answerOptions = {
            {"Stockholm", "Buenos Aires", "Sao Paul", "Brasilia"},
            {"Donald Trump", "Kamala Harris", "Joe Biden", "John F.Kennedy"}

    };
    String[] correctAnswers = {
            "D",
            "C",

    };

    int index;
    String answer;
    int correctGuesses = 0;
    int totalQuestions = questions.length;
    int seconds = 10;

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
    private Label timeLeft;
    @FXML
    private Label genreField;

    public GameController() {
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genreField.setText(genre[index]);
        questionArea.setText(questions[index]);
        buttonA.setText(answerOptions[index][0]);
        buttonB.setText(answerOptions[index][1]);
        buttonC.setText(answerOptions[index][2]);
        buttonD.setText(answerOptions[index][3]);

    }

    @FXML
    public void nextQuestion() {

        if (index >= totalQuestions) {
            results();
        } else {
            genreField.setText(genre[index]);
            questionArea.setText(questions[index]);
            buttonA.setText(answerOptions[index][0]);
            buttonB.setText(answerOptions[index][1]);
            buttonC.setText(answerOptions[index][2]);
            buttonD.setText(answerOptions[index][3]);
            // timer.start();

        }

    }

    @FXML
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

    @FXML
    public void displayAnswer() {
        //timer.stop();
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
            //timeLeft.setText(String.valueOf(seconds));
            buttonA.setDisable(false);
            buttonB.setDisable(false);
            buttonC.setDisable(false);
            buttonD.setDisable(false);
            index++;

            nextQuestion();
        }));
        time.setAutoReverse(false);
        time.play();

    }

    public void results() {
        //timer.stop();
        buttonA.setDisable(false);
        buttonB.setDisable(false);
        buttonC.setDisable(false);
        buttonD.setDisable(false);

        genreField.setVisible(false);
        questionArea.setVisible(true);
        questionArea.setStyle("-fx-text-fill: #00ff04;");
        buttonA.setVisible(false);
        buttonB.setVisible(false);
        buttonC.setVisible(false);
        buttonD.setVisible(false);
        timeLeft.setVisible(false);
        //timeLabel.setVisible(false);

        if ((double) correctGuesses / (double) totalQuestions > 0.5) {
            questionArea.setFont(Font.font("Avenir Next", BOLD, 20));
            questionArea.setText("BRA JOBBAT!! " + "\n" + correctGuesses + " rätt av " + totalQuestions + " möjliga");
        } else {
            questionArea.setFont(Font.font("Avenir Next", BOLD, 20));
            questionArea.setText("BÄTTRE KAN DU! " + "\n" + correctGuesses + " rätt av " + totalQuestions + " möjliga");
        }

    }


}








