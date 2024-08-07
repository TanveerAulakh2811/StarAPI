module com.example.starapi {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.net.http;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.starapi to javafx.fxml;
    exports com.example.starapi;
}