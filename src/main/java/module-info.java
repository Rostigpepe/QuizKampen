module com.example.quizkampennewfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens gameControllers to javafx.fxml;
    exports gameControllers;
}