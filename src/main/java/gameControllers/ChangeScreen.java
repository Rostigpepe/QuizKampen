package gameControllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class ChangeScreen implements Initializable {


    public static ObjectInputStream inputStreamer;
    public static ObjectOutputStream outputStreamer;
    public static String name;

    public static final String CATEGORY = "category-view.fxml";
    public static final String GAME_CONTROLLER = "gameController-view.fxml";
    public static final String LOGIN_CONTROLLER = "login-view.fxml";
    public static final String CHOOSE_GAME_CONTROLLER = "chooseGame-view.fxml";
    public static final String RESULTS = "results-view.fxml";
    public static final String WAITING = "waiting-view.fxml";

    public static void setInputStreamer(ObjectInputStream inputStreamer) {
        ChangeScreen.inputStreamer = inputStreamer;
    }

    public static void setOutputStreamer(ObjectOutputStream outputStreamer) {
        ChangeScreen.outputStreamer = outputStreamer;
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void newScreen(String fxml, Node node) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(fxml));
        Stage primaryStage = (Stage) node.getScene().getWindow();
        primaryStage.setTitle("Quizkampen");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}