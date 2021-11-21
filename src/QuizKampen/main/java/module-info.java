module com.example.quizkampennewfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens gameControllers to javafx.fxml;
    exports gameControllers;
}