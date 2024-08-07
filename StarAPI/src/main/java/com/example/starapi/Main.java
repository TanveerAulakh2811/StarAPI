package com.example.starapi;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Correct path to FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/starapi/hello-view.fxml"));
        Scene scene = new Scene(loader.load(), 800, 600);

        // Correct path to CSS file
        scene.getStylesheets().add(getClass().getResource("/com/example/starapi/styles.css").toExternalForm());

        Image icon = new Image(getClass().getResourceAsStream("/com/example/starapi/logo.png"));
        primaryStage.getIcons().add(icon);


        primaryStage.setTitle("Star Information");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
