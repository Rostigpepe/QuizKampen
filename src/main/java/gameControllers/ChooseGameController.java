package gameControllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ChooseGameController implements Initializable {
    @FXML
    private Button loggaUt;
    @FXML
    private ComboBox comboBox;
    @FXML
    private ComboBox comboBox2;
    Utilities utilities = new Utilities();

    public ChooseGameController() {
    }

    public void myBox(ActionEvent event) throws IOException {
        if (this.comboBox.getItems() != null) {
            System.out.println(this.comboBox.getSelectionModel().selectedItemProperty().getValue());
        }

    }

    public void loggaUtKnappenOnAction(ActionEvent event) {
        Stage stage = (Stage) this.loggaUt.getScene().getWindow();
        stage.close();
    }

    public void initialize(URL url, ResourceBundle rb) {
        this.comboBox2.getItems().addAll(Utilities.getProperty("Fragor:").split(","));
        this.comboBox.getItems().addAll(Utilities.getProperty("Ronder:").split(","));
    }

    public void startaSpel(ActionEvent e) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(ChangeScreen.GAME_CONTROLLER)));
            Stage primaryStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            primaryStage.setTitle("Quizkampen");
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);
            primaryStage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
