package gameControllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import gameControllers.UserInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController {
    @FXML
    private Button avslutaButton;
    @FXML
    private Label loginLabel;
    @FXML
    private ImageView QuizLoggaImageView;
    @FXML
    private ImageView lockImageView;
    @FXML
    private TextField användarnamnTextField;
    @FXML
    private PasswordField lösenordField;

    public LoginController() {
    }

    public void initialisering(URL url, ResourceBundle resourceBundle) {
        File quizFile = new File("Bilder/QuizKampen.jpg");
        Image quizImage = new Image(quizFile.toURI().toString());
        this.QuizLoggaImageView.setImage(quizImage);
        File bokFile = new File("Bilder/BlåBok.jpg");
        Image bokImage = new Image(bokFile.toURI().toString());
        this.QuizLoggaImageView.setImage(bokImage);
    }

    public void loginKnappenOnAction(ActionEvent event) throws InterruptedException, IOException {
        if (!this.användarnamnTextField.getText().isBlank() && !this.lösenordField.getText().isBlank()) {
            if (this.användarnamnTextField.getText().equals("h") && this.lösenordField.getText().equals("h")) {
                this.loginLabel.setText("Lyckad inloggning!");
                new UserInfo();
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(ChangeScreen.CHOOSE_GAME_CONTROLLER)));
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                primaryStage.setTitle("Quizkampen");
                primaryStage.setScene(new Scene(root));
                primaryStage.setResizable(false);
                primaryStage.show();

            } else {
                this.loginLabel.setText("Felaktig information");
            }
        } else {
            this.loginLabel.setText("Vänligen ange användarnamn och lösenord");
        }

    }

    public void avslutaKnappenOnAction(ActionEvent event) {
        Stage stage = (Stage)this.avslutaButton.getScene().getWindow();
        stage.close();
    }

    public void bekräftaLogin() {
    }


}