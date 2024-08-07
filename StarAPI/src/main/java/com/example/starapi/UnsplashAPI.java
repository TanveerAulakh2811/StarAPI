package com.example.starapi;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;



public class UnsplashAPI {

    private static final String ACCESS_KEY = "CSSClBI_pLjfiMn0hx3UwIDnIuf3Q-5ke0oXpw0VwEk";
    private static final String BASE_URL = "https://api.unsplash.com/search/photos?query=";

    public static JsonObject searchStarImages(String starName) throws Exception {
        // Encode starName to handle spaces and special characters
        String encodedStarName = URLEncoder.encode(starName + " star sky", StandardCharsets.UTF_8.toString());
        String urlString = BASE_URL + encodedStarName + "&client_id=" + ACCESS_KEY;

        // Print the URL for debugging
        System.out.println("Unsplash API Request URL: " + urlString);

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Check for response code
        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new Exception("Error: Received HTTP response code " + responseCode);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();

        // Print raw response for debugging
        System.out.println("Unsplash API Response: " + response.toString());

        // Parse JSON response
        return JsonParser.parseString(response.toString()).getAsJsonObject();
    }
}
