package dev.degallant.cache;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Makes request to get resources from reqres.in
 */
public class ResourceServer {

    private static final String baseURL = "https://reqres.in/api/";

    public String getResource(int id) {
        return makeRequest("unknown/" + id);
    }

    protected String makeRequest(String endpoint) {

        try {
            URL url = new URL(baseURL + endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("accept", "application/json");
            InputStream responseStream = connection.getInputStream();
            return new String(responseStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new RuntimeException("Failed to make request: " + exception.getMessage(), exception);
        }

    }

}
