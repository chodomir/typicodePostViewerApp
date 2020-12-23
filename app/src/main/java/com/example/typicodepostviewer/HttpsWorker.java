package com.example.typicodepostviewer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.Callable;

import javax.net.ssl.HttpsURLConnection;

public class HttpsWorker implements Callable<String> {
    private static final String URL_ADDR = "https://jsonplaceholder.typicode.com/";

    private String query;
    // @TODO: Test query to start with either 'posts/...' or 'users/...' ?!
    HttpsWorker(String query) {
        this.query = query;
    }

    @Override
    public String call() throws Exception {
        // Request API call
        URL url = new URL(URL_ADDR + query);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.connect();

        // Read response
        InputStream in = new BufferedInputStream(connection.getInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder response = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        // free resources
        connection.disconnect();
        reader.close();

        // return response
        return response.toString();
    }
}
