package gameControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CategoryController implements Initializable {

    GameController gc = new GameController();

    String[] category = gc.genre;


    @FXML
    private Button category1;

    @FXML
    private Button category2;

    @FXML
    private Button category3;


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        category1.setText(category[0]);
        category2.setText(category[1]);
        category3.setText(category[2]);

    }

    @FXML
    public void categoryChosen(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(ChangeScreen.GAME_CONTROLLER)));
        Stage primaryStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        primaryStage.setTitle("Quizkampen");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();


    }

}












