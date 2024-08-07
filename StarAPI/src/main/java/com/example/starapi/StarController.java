package com.example.starapi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.Platform;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class StarController {

    @FXML
    private TextField starNameField;
    @FXML
    private Label resultLabel;
    @FXML
    private ImageView constellationImageView;
    @FXML
    private Label imageDescriptionLabel;
    @FXML
    private Label statusLabel;

    @FXML
    private void handleFetchStarInfo() {
        String starName = starNameField.getText();
        if (starName.isEmpty()) {
            statusLabel.setText("Please enter a star name.");
            return;
        }

        statusLabel.setText("Status: Loading...");
        new Thread(() -> {
            try {
                JsonObject starInfo = StarAPI.getStarInfo(starName);
                if (starInfo == null) {
                    Platform.runLater(() -> {
                        resultLabel.setText("No data found for the given star name.");
                        statusLabel.setText("Status: No data found.");
                    });
                    return;
                }

                String name = starInfo.get("name").getAsString();
                String constellation = starInfo.get("constellation").getAsString();
                String rightAscension = starInfo.get("right_ascension").getAsString();
                String declination = starInfo.get("declination").getAsString();
                String apparentMagnitude = starInfo.get("apparent_magnitude").getAsString();
                String absoluteMagnitude = starInfo.get("absolute_magnitude").getAsString();
                String distanceLightYear = starInfo.get("distance_light_year").getAsString();
                String spectralClass = starInfo.get("spectral_class").getAsString();

                String resultText = String.format("Name: %s\nConstellation: %s\nRight Ascension: %s\nDeclination: %s\nApparent Magnitude: %s\nAbsolute Magnitude: %s\nDistance (Light Years): %s\nSpectral Class: %s",
                        name, constellation, rightAscension, declination, apparentMagnitude, absoluteMagnitude, distanceLightYear, spectralClass);

                Platform.runLater(() -> {
                    resultLabel.setText(resultText);
                    statusLabel.setText("Status: Loaded");
                });

                // Fetch constellation images
                handleFetchConstellationImages(starName);

            } catch (Exception ex) {
                Platform.runLater(() -> {
                    resultLabel.setText("Error fetching star info");
                    statusLabel.setText("Status: Error");
                });
                ex.printStackTrace();
            }
        }).start();
    }

    @FXML
    private void handleFetchConstellationImages(String starName) {
        new Thread(() -> {
            try {
                JsonObject responseData = UnsplashAPI.searchStarImages(starName);
                if (responseData == null) {
                    Platform.runLater(() -> statusLabel.setText("No data found for constellation images."));
                    return;
                }

                JsonArray results = responseData.getAsJsonArray("results");
                if (results.size() > 0) {
                    JsonObject firstResult = results.get(0).getAsJsonObject();
                    String imageUrl = firstResult.getAsJsonObject("urls").get("regular").getAsString();
                    String description = firstResult.get("alt_description").getAsString();

                    Image image = new Image(imageUrl);

                    Platform.runLater(() -> {
                        imageDescriptionLabel.setText(description);
                        constellationImageView.setImage(image);
                    });
                } else {
                    Platform.runLater(() -> statusLabel.setText("No constellation images found"));
                }
            } catch (Exception ex) {
                Platform.runLater(() -> statusLabel.setText("Error fetching constellation images"));
                ex.printStackTrace();
            }
        }).start();
    }

    @FXML
    private void handleExampleStar(ActionEvent event) {
        Button source = (Button) event.getSource();
        String starName = source.getText();
        starNameField.setText(starName);
        handleFetchStarInfo();
    }
}
