package com.example.starapi;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class StarAPI {

    private static final String API_URL = "https://api.api-ninjas.com/v1/stars";
    private static final String API_KEY = "1c0HX9CBXURN0lpz1hlrzA==y0gHaFo3H3vtEy03";

    public static JsonObject getStarInfo(String starName) throws Exception {
        String urlString = API_URL + "?name=" + starName;
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("X-Api-Key", API_KEY);

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
        System.out.println("Star API Response: " + response.toString());

        // Parse JSON response
        JsonArray jsonArray = JsonParser.parseString(response.toString()).getAsJsonArray();

        if (jsonArray.size() > 0) {
            return jsonArray.get(0).getAsJsonObject();
        } else {
            throw new Exception("No data found for the given star name.");
        }
    }
}
