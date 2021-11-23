package gameControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import org.w3c.dom.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ResultsController  implements Initializable {

    public VBox background;
    GameController g = new GameController();
    ChangeScreen screen = new ChangeScreen();

    int correctGuesses = g.getCorrectGuesses();
    int totalQuestions = g.getTotalQuestions();

    @FXML
    private Text scores;
    @FXML
    private Text scores2;




    public void initialize(URL url, ResourceBundle resourceBundle) {




    }

    public void results() {

    }

    public void addToRounds() {
        results();
    }



}
